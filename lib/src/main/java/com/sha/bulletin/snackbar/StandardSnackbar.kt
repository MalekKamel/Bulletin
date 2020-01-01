package com.sha.bulletin.snackbar

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.DuplicateStrategy

class StandardSnackbar(activity: FragmentActivity,
                       coordinatorLayout: View,
                       text: String,
                       duration: Int): BulletinSnackbar(activity, coordinatorLayout, text, duration) {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String by lazy { options.content }
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    override fun setup(snackbar: Snackbar) {
        options.setup?.invoke(snackbar)
    }

    override fun onDismiss() {
        super.onDismiss()
        options.onDismiss?.invoke()
    }

    data class Options(
            var title: String = "",
            var content: String = "",
            var setup: ((Snackbar) -> Unit)? = null,
            var onDismiss: (() -> Unit)? = null,
            var duplicateStrategy: DuplicateStrategy = BulletinConfig.duplicateStrategy
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
             * Callback invoked on clicking dismiss button
             */
            fun setup(callback: ((Snackbar) -> Unit)?): Builder {
                options.setup = callback
                return this
            }

            /**
             * Callback invoked on clicking dismiss button
             */
            fun onDismiss(callback: (() -> Unit)?): Builder {
                options.onDismiss = callback
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
            fun create(block: Options.() -> Unit) = Options().apply { block() }
        }
    }

    companion object {
        /**
         * Create the [Bulletin]
         * @param block DSL for creating the options
         */
        fun create(
                activity: FragmentActivity,
                coordinatorLayout: View,
                text: String,
                duration: Int,
                block: Options.() -> Unit): StandardSnackbar {
            return StandardSnackbar(activity, coordinatorLayout, text, duration)
                    .apply { options = Options().apply { block() } }
        }
        /**
         * Create the [Bulletin]
         * @param options for the bulletin
         */
        fun create(
                activity: FragmentActivity,
                coordinatorLayout: View,
                duration: Int,
                options: Options) = StandardSnackbar(activity, coordinatorLayout, options.content, duration)
                .apply { this.options = options }
    }
}