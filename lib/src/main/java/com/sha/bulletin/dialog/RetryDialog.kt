package com.sha.bulletin.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sha.bulletin.*

class RetryDialog : BulletinDialog() {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String by lazy { options.content }
    override var layoutId: Int = R.layout.frag_dialog_retry
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    private val tvTitle: TextView by lazy { view!!.findViewById<TextView>(R.id.tvTitle) }
    private val tvContent: TextView by lazy { view!!.findViewById<TextView>(R.id.tvContent) }
    private val btnDismiss: Button by lazy { view!!.findViewById<Button>(R.id.btnDismiss) }
    private val btnRetry: Button by lazy { view!!.findViewById<Button>(R.id.btnRetry) }
    private val iconContainer: IconContainer by lazy { view!!.findViewById<IconContainer>(R.id.iconContainer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellableOnTouchOutside)

        iconContainer.setup(options.iconSetup)

        tvTitle.apply {
            if(options.title.isEmpty()) this.visibility = View.GONE
            else text = options.title
        }

        tvContent.text = options.content

        btnRetry.setOnClickListener {
            options.onRetry?.invoke()
            dismiss()
        }

        btnDismiss.setOnClickListener {
            options.onDismissClicked?.invoke()
            dismiss()
        }
    }

    data class Options(
            var title: String = "",
            var content: String = "",
            var onRetry: (() -> Unit)? = null,
            var onDismissClicked: (() -> Unit)? = null,
            var isCancellableOnTouchOutside: Boolean = BulletinConfig.isCancellableOnTouchOutside,
            var duplicateStrategy: DuplicateStrategy = BulletinConfig.duplicateStrategy,
            var iconSetup: IconSetup = BulletinConfig.iconSetup
    ){
        class Builder {
            private val options = Options()

            /**
             * Title for the [Bulletin]
             */
            fun title(title: String): Builder {
                options.title = title
                return this
            }

            /**
             * Content to be displayed
             */
            fun content(content: String): Builder {
                options.content = content
                return this
            }

            /**
             * Callback invoked on clicking retry button
             */
            fun onRetry(callback: (() -> Unit)?): Builder {
                options.onRetry = callback
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
             * If true, the [Bulletin] can be cancelled on touch outside
             */
            fun isCancellableOnTouchOutside(cancellable: Boolean): Builder {
                options.isCancellableOnTouchOutside = cancellable
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

            /**
             * Setup icon
             */
            fun iconSetup(setup: IconSetup): Builder {
                options.iconSetup = setup
                return this
            }

            /**
             * Returns an instance of this options
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
            fun create(content: String, block: Options.() -> Unit) = Options().apply {
                this.content = content
                block()
            }
        }
    }

    companion object {
        /**
         * Create the [Bulletin]
         * @param block DSL for creating the options
         */
        fun create(block: Options.() -> Unit) = RetryDialog().apply { options = Options().apply { block() } }

        /**
         * Create the [Bulletin]
         * @param options for the [Bulletin]
         */
        fun create(options: Options) = RetryDialog().apply { this.options = options }
    }
}


