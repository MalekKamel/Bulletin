package com.sha.bulletin.sheet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.*

class InfoSheet : AbstractSheet() {
    var options: Options = Options.default()
    override val name: String = javaClass.name
    override val content: String = options.content
    override var layoutId: Int = R.layout.frag_sheet_info

    private val tvTitle: TextView by lazy { view!!.findViewById<TextView>(R.id.tvTitle) }
    private val tvContent: TextView by lazy { view!!.findViewById<TextView>(R.id.tvContent) }
    private val btnDismiss: Button by lazy { view!!.findViewById<Button>(R.id.btnDismiss) }
    private val iconContainer: IconContainer by lazy { view!!.findViewById<IconContainer>(R.id.iconContainer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellable)

        iconContainer.setup(options.iconSetup)

        tvTitle.apply {
            if(options.title.isEmpty()) this.visibility = View.GONE
            else text = options.title
        }

        tvContent.text = options.content

        btnDismiss.setOnClickListener {
            options.dismissCallback?.invoke()
            dismiss()
        }
    }

    data class Options(
            var title: String = "",
            var content: String = "",
            var retryCallback: (() -> Unit)? = null,
            var dismissCallback: (() -> Unit)? = null,
            var isCancellable: Boolean = true,
            var ignoreIfSameContentDisplayed: Boolean = true,
            var contentType: ContentType = ContentType.INFO,
            var iconSetup: IconSetup = IconSetup.default()
    ){
        class Builder {
            private val options = Options()

            fun content(content: String): Builder {
                options.content = content
                return this
            }

            fun retryCallback(callback: (() -> Unit)?): Builder {
                options.retryCallback = callback
                return this
            }

            fun dismissCallback(callback: (() -> Unit)?): Builder {
                options.dismissCallback = callback
                return this
            }

            fun isCancellable(cancellable: Boolean): Builder {
                options.isCancellable = cancellable
                return this
            }

            fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
                options.ignoreIfSameContentDisplayed = ignore
                return this
            }

            fun contentType(type: ContentType): Builder {
                options.contentType = type
                return this
            }

            fun title(title: String): Builder {
                options.title = title
                return this
            }

            fun iconSetup(setup: IconSetup): Builder {
                options.iconSetup = setup
                return this
            }

            fun build() = options
        }

        companion object {
            fun default(): Options = Builder().build()
            fun create(block: Options.() -> Unit) = Options().apply { block() }
        }
    }

    companion object {
        fun create(block: Options.() -> Unit) = InfoSheet().apply { options = Options().apply { block() } }
        fun create(options: Options) = InfoSheet().apply { this.options = options }
    }

    fun show(activity: FragmentActivity) {
        if (options.ignoreIfSameContentDisplayed && isBulletinWithContentDisplayed(content)) return
        super.show(activity, javaClass.name)
    }
}

