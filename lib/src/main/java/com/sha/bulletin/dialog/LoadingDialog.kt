package com.sha.bulletin.dialog

import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.R

class LoadingDialog : BulletinDialog() {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String = options.content
    override var layoutId: Int = R.layout.frag_dialog_loading
    override fun isCancelable(): Boolean  = options.isCancellableOnTouchOutside
    private val tvContent: TextView by lazy { view!!.findViewById<TextView>(R.id.tvContent) }
    override var ignoreIfSameContentDisplayed: Boolean = options.ignoreIfSameContentDisplayed

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellableOnTouchOutside)
        tvContent.text = options.content
    }
    
    data class Options(
            var content: String = "",
            var onDismissClicked: (() -> Unit)? = null,
            var ignoreIfSameContentDisplayed: Boolean = BulletinConfig.ignoreIfSameContentDisplayed,
            var isCancellableOnTouchOutside: Boolean = BulletinConfig.isCancellableOnTouchOutside
    ){

        class Builder {
            private val options = Options()

            /**
             * Content to be displayed
             */
            fun content(content: String): Builder {
                options.content = content
                return this
            }

            /**
             * If true, the [Bulletin] can be cancelled on touch outside
             */
            fun isCancellableOnTouchOutside(cancellable: Boolean): Builder {
                options.isCancellableOnTouchOutside = cancellable
                return this
            }

            /**
             * If true, this [Bulletin] won't be displayed if there's another bulletin displayed
             * with the same name and content of this bulletin
             */
            fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
                options.ignoreIfSameContentDisplayed = ignore
                return this
            }

            /**
             * Callback invoked on clicking dismiss button
             */
            fun onDismissClicked(callback: (() -> Unit)?): Builder {
                options.onDismissClicked = callback
                return this
            }

            /**
             * Returns an instance of this bulletin
             */
            fun build() = options
        }

        companion object {
            /**
             * Create the default options
             */
            fun default(): Options = Builder().build()
            /**
             * Create the options
             * @param block DSL for creating the options
             */
            fun create(block: Options.() -> Unit) = Options().apply { block() }
        }
    }

    companion object {
        /**
         * Create the [Bulletin]
         * @param block DSL for creating the options
         */
        fun create(block: Options.() -> Unit) = LoadingDialog().apply { options = Options().apply { block() } }

        /**
         * Create the [Bulletin]
         * @param options for the [Bulletin]
         */
        fun create(options: Options) = LoadingDialog().apply { this.options = options }
    }
}
