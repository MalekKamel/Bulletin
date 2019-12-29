package com.sha.bulletin.flashbar

import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinConfig

class StandardFlashBar(builder: Builder): BulletinFlashBar(builder) {
    var options: Options = Options.default()

    override val name: String = javaClass.name
    override val content: String = builder.message ?: ""
    override var ignoreIfSameContentDisplayed: Boolean = options.ignoreIfSameContentDisplayed

    data class Options(
            var ignoreIfSameContentDisplayed: Boolean = BulletinConfig.ignoreIfSameContentDisplayed
    ){
        class Builder {
            private val options = Options()

            /**
             * If true, this [Bulletin] won't be displayed if there's another [Bulletin] displayed
             * with the same name and content of this [Bulletin]
             */
            fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
                options.ignoreIfSameContentDisplayed = ignore
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