package com.sha.bulletin.sheet

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.sha.bulletin.*

class InfoSheet : BulletinSheet() {
    var options: Options = Options.default()
    override val name: String = javaClass.name
    override val content: String by lazy { options.content }
    override var layoutId: Int = R.layout.sheet_info
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    private val tvTitle: TextView by lazy { view!!.findViewById<TextView>(R.id.tvTitle) }
    private val tvContent: TextView by lazy { view!!.findViewById<TextView>(R.id.tvContent) }
    private val btnDismiss: Button by lazy { view!!.findViewById<Button>(R.id.btnDismiss) }
    private val iconContainer: IconContainer by lazy { view!!.findViewById<IconContainer>(R.id.iconContainer) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        dialog?.setCanceledOnTouchOutside(options.isCancellableOnTouchOutside)

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

        options.setupTitleTextView?.invoke(tvTitle)
        options.setupContentTextView?.invoke(tvContent)
    }

    data class Options(
            var title: String = "",
            var content: String = "",
            var setupTitleTextView: ((TextView) -> Unit)? = null,
            var setupContentTextView: ((TextView) -> Unit)? = null,
            var dismissCallback: (() -> Unit)? = null,
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
             * Customize title [TextView] here.
             */
            fun setupTitleTextView(callback: ((TextView) -> Unit)?): Builder {
                options.setupTitleTextView = callback
                return this
            }

            /**
             * Customize content [TextView] here.
             */
            fun setupContentTextView(callback: ((TextView) -> Unit)?): Builder {
                options.setupContentTextView = callback
                return this
            }

            /**
             * Callback invoked on dismiss
             */
            fun dismissCallback(callback: (() -> Unit)?): Builder {
                options.dismissCallback = callback
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
             * Returns an instance of this [Bulletin]
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
        fun create(block: Options.() -> Unit) = InfoSheet().apply { options = Options().apply { block() } }

        /**
         * Create the [Bulletin]
         * @param options for the [Bulletin]
         */
        fun create(options: Options) = InfoSheet().apply { this.options = options }
    }

}

