package com.sha.bulletin.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.R
import com.sha.bulletin.isBulletinWithContentDisplayed

class RetryDialog : AbstractDialog() {
    var options: Options = Options.defaultOptions()
        set(value) {
            if (isDisplayed) return
            field = value
        }
    override val name: String = javaClass.name
    override val content: String = options.content ?: ""
    override var layoutId: Int = R.layout.frag_dialog_retry

    private val tvTitle: TextView? = view?.findViewById(R.id.tvTitle)
    private val tvContent: TextView? = view?.findViewById(R.id.tvContent)
    private val btnRetry: Button = view!!.findViewById(R.id.btnRetry)
    private val btnDismiss: TextView = view!!.findViewById(R.id.btnDismiss)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellable)
        options.content?.let { tvContent?.text = it }
        btnRetry.setOnClickListener {
            options.retryCallback?.invoke()
            dismiss()
        }

        btnDismiss.setOnClickListener {
            options.dismissCallback?.invoke()
            dismiss()
        }
    }

    data class Options(
            var retryCallback: (() -> Unit)? = null,
            var dismissCallback: (() -> Unit)? = null,
            var isCancellable: Boolean = true,
            var ignoreIfSameContentDisplayed: Boolean = true,
            var title: String? = null,
            var content: String? = null
    ){
        class Builder {
            private val options = Options()

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

            fun content(content: String?): Builder {
                options.content = content
                return this
            }

            fun title(title: String?): Options.Builder {
                options.title = title
                return this
            }

            fun build() = options
        }

        companion object {
            fun defaultOptions(): Options = Builder().build()
            fun create(content: String?, block: Options.() -> Unit) = Options().apply {
                this.content = content
                block()
            }
        }
    }

    companion object {
        fun create(block: Options.() -> Unit) = RetryDialog().apply { options = Options().apply { block() } }

        fun create(options: Options) = RetryDialog().apply { this.options = options }
    }

    fun show(activity: FragmentActivity) {
        if (options.ignoreIfSameContentDisplayed && isBulletinWithContentDisplayed(content)) return
        super.show(activity, javaClass.name)
    }
}


