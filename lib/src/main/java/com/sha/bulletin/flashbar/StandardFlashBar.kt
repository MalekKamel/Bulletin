package com.sha.bulletin.flashbar

import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.DuplicateStrategy

class StandardFlashBar(builder: Builder): BulletinFlashBar(builder) {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String by lazy { builder.message ?: "" }
    override var duplicateStrategy: DuplicateStrategy = options.duplicateStrategy

    data class Options(
            var duplicateStrategy: DuplicateStrategy = BulletinConfig.duplicateStrategy
    ){
        class Builder {
            private val options = Options()

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
         * Create the bulletin
         * @param block DSL for creating the options
         */
        fun create(builder: Builder, block: Options.() -> Unit): StandardFlashBar {
           return StandardFlashBar(builder).apply { options = Options().apply { block() } }
        }

        /**
         * Create the bulletin
         * @param options for the bulletin
         */
        fun create(builder: Builder, options: Options): StandardFlashBar {
           return StandardFlashBar(builder).apply { this.options = options }
        }
    }

}