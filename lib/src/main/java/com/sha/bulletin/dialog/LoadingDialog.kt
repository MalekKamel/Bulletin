package com.sha.bulletin.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.R
import com.sha.bulletin.isBulletinWithContentDisplayed

class LoadingDialog : AbstractDialog() {
    var options: Options = Options.defaultOptions()
        set(value) {
            if (isDisplayed) return
            field = value
        }
    override val name: String = javaClass.name
    override val content: String = options.content
    override var layoutId: Int = R.layout.frag_dialog_loading
    override fun isCancelable(): Boolean  = options.isCancellable
    private val tvContent: TextView = view!!.findViewById(R.id.tvContent)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellable)
        tvContent.text = options.content
    }
    
    data class Options(
            var content: String = "",
            var dismissCallback: (() -> Unit)? = null,
            var ignoreIfSameContentDisplayed: Boolean = true,
            var isCancellable: Boolean = false
    ){

        class Builder {
            private val options = Options()

            fun content(content: String): Builder {
                options.content = content
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

            fun dismissCallback(callback: (() -> Unit)?): Builder {
                options.dismissCallback = callback
                return this
            }

            fun build() = options
        }

        companion object {
            fun defaultOptions(): Options = Builder().build()
            fun create(block: Options.() -> Unit) = Options().apply { block() }
        }
    }

    companion object {
        fun create(block: Options.() -> Unit) = LoadingDialog().apply { options = Options().apply { block() } }
        fun create(options: Options) = LoadingDialog().apply { this.options = options }
    }

    fun show(activity: FragmentActivity) {
        if (options.ignoreIfSameContentDisplayed && isBulletinWithContentDisplayed(content)) return
        super.show(activity, javaClass.name)
    }
}
