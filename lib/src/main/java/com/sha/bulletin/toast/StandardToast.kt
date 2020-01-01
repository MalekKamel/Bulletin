package com.sha.bulletin.toast

import android.view.LayoutInflater
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.DuplicateStrategy
import com.sha.bulletin.R

class StandardToast(activity: FragmentActivity): BulletinToast(activity) {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String by lazy { options.content }
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    data class Options(
            var content: String = "",
            var duplicateStrategy: DuplicateStrategy = BulletinConfig.duplicateStrategy
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
            fun create(block: Options.() -> Unit) = Options().apply(block)
        }
    }

    companion object {
        /**
         * Create the bulletin
         * @param block DSL for creating the options
         */
        fun create(activity: FragmentActivity, block: Options.() -> Unit): StandardToast {
           return create(activity, Options().apply(block), null)
        }

        /**
         * Create the bulletin
         */
        fun create(activity: FragmentActivity, options: Options): StandardToast {
            return create(activity, options, null)
        }

        /**
         * Create the bulletin
         * @param options for the bulletin
         */
        fun create(activity: FragmentActivity,
                   options: Options,
                   block: (StandardToast.() -> Unit)?): StandardToast {
           return StandardToast(activity).apply {
               view = LayoutInflater.from(activity).inflate(R.layout.toast, null)
               view.findViewById<TextView>(R.id.message).text = options.content
               block?.invoke(this)
               this.options = options
           }
        }
    }


}