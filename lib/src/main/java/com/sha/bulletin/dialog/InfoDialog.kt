package com.sha.bulletin.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.ContentType
import com.sha.bulletin.R
import com.sha.bulletin.isBulletinWithContentDisplayed

class InfoDialog : AbstractDialog() {
    var options: Options = Options.defaultOptions()
        set(value) {
            if (isDisplayed) return
            field = value
        }

    override var layoutId: Int = R.layout.frag_dialog_info
    override val name: String = javaClass.name
    override val content: String = options.content

    private val tvTitle: TextView = view!!.findViewById(R.id.tvTitle)
    private val tvContent: TextView =  view!!.findViewById(R.id.tvContent)
    private val btnDismiss: Button = view!!.findViewById(R.id.btnDismiss)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellable)
        tvContent.text = options.content
        tvTitle.text = options.title

        var color = -1
            when (options.contentType) {
                ContentType.WARNING -> {
                    color = R.color.warning
                    tvContent.setTextColor(ContextCompat.getColor(context!!, R.color.warning))
                }
                ContentType.ERROR -> color = R.color.exception
                else -> {}
            }

        if (color != -1) tvContent.setTextColor(ContextCompat.getColor(context!!, color))

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
            var contentType: ContentType? = null
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

            fun contentType(type: ContentType?): Builder {
                options.contentType = type
                return this
            }

            fun title(title: String): Builder {
                options.title = title
                return this
            }

            fun build() = options
        }

        companion object {
            fun defaultOptions(): Options = Builder().build()
            fun create(type: ContentType, block: Options.() -> Unit) = Options().apply {
                contentType = type
                block()
            }
        }
    }

    companion object {
        fun create(block: Options.() -> Unit) = InfoDialog().apply { options = Options().apply { block() } }
        fun create(options: Options) = InfoDialog().apply { this.options = options }
    }

    fun show(activity: FragmentActivity) {
        if (options.ignoreIfSameContentDisplayed && isBulletinWithContentDisplayed(content)) return
        super.show(activity, javaClass.name)
    }
}

