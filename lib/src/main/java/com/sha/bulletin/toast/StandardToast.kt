package com.sha.bulletin.toast

import android.content.Context
import android.view.LayoutInflater
import android.widget.TextView
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.DuplicateStrategy
import com.sha.bulletin.R

class StandardToast(context: Context): BulletinToast(context) {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String = options.content
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
        fun create(context: Context, block: Options.() -> Unit): StandardToast {
           return create(context, Options().apply(block), null)
        }

        /**
         * Create the bulletin
         */
        fun create(context: Context, options: Options): StandardToast {
            return create(context, options, null)
        }

        /**
         * Create the bulletin
         * @param options for the bulletin
         */
        fun create(context: Context,
                   options: Options,
                   block: (StandardToast.() -> Unit)?): StandardToast {
           return StandardToast(context).apply {
               view = LayoutInflater.from(context).inflate(R.layout.toast, null)
               view.findViewById<TextView>(R.id.message).text = options.content
               block?.invoke(this)
               this.options = options
           }
        }
    }


}