package com.sha.bulletin.flashbar

import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinConfig
import com.sha.bulletin.bulletins
import com.sha.bulletin.isBulletinDisplayed

class BulletinFlashBar(builder: Builder): Flashbar(builder), Bulletin {
    override val name: String = javaClass.name
    override val content: String = builder.message ?: ""
    var options: Options = Options.default()

    /**
     * Show this bulletin
     */
    override fun show() {
        if (options.ignoreIfSameContentDisplayed && isBulletinDisplayed(name, content)) return
        super.show()
        bulletins.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bulletins.remove(this)
    }

    data class Options(
            var ignoreIfSameContentDisplayed: Boolean = BulletinConfig.ignoreIfSameContentDisplayed
    ){
        class Builder {
            private val options = Options()

            /**
             * If true, this bulletin won't be displayed if there's another bulletin displayed
             * with the same name and content of this bulletin
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
        fun create(builder: Builder, block: Options.() -> Unit): BulletinFlashBar {
           return BulletinFlashBar(builder).apply { options = Options().apply { block() } }
        }

        /**
         * Create the bulletin
         * @param options for the bulletin
         */
        fun create(builder: Builder, options: Options): BulletinFlashBar {
           return BulletinFlashBar(builder).apply { this.options = options }
        }
    }

}