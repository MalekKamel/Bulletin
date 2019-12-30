package com.sha.bulletin.sheet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sha.bulletin.*

class RetrySheet : BulletinSheet() {
    var options: Options = Options.default()
    override val name: String = javaClass.name
    override val content: String = options.content
    override var layoutId: Int = R.layout.frag_dialog_retry
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    private val tvTitle: TextView by lazy { view!!.findViewById<TextView>(R.id.tvTitle) }
    private val tvContent: TextView by lazy { view!!.findViewById<TextView>(R.id.tvContent) }
    private val btnDismiss: Button by lazy { view!!.findViewById<Button>(R.id.btnDismiss) }
    private val btnRetry: Button by lazy { view!!.findViewById<Button>(R.id.btnRetry) }
    private val iconContainer: IconContainer by lazy { view!!.findViewById<IconContainer>(R.id.iconContainer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellable)

        iconContainer.setup(options.iconSetup)

        tvTitle.apply {
            if(options.title.isEmpty()) this.visibility = View.GONE
            else text = options.title
        }

        tvContent.text = options.content
     
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
            var title: String = "",
            var content: String = "",
            var retryCallback: (() -> Unit)? = null,
            var dismissCallback: (() -> Unit)? = null,
            var isCancellable: Boolean = BulletinConfig.isCancellableOnTouchOutside,
            var duplicateStrategy: DuplicateStrategy = BulletinConfig.duplicateStrategy,
            var iconSetup: IconSetup = BulletinConfig.iconSetup
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

            /**
             * [DuplicateStrategy] for managing duplicate bulletins. You can choose
             * one of many implementations in the library or implement your own strategy.
             */
            fun duplicateStrategy(strategy: DuplicateStrategy): Builder {
                options.duplicateStrategy = strategy
                return this
            }

            fun content(content: String): Builder {
                options.content = content
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
            fun create(message: String, block: Options.() -> Unit) = Options().apply {
                this.content = message
                block()
            }
        }
    }

    companion object {
        fun create(block: Options.() -> Unit) = RetrySheet().apply { options = Options().apply { block() } }
        fun create(options: Options) = RetrySheet().apply { this.options = options }
    }

}


