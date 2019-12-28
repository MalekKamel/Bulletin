package com.sha.bulletin

import com.andrognito.flashbar.Flashbar

class BulletinFlashBar constructor(builder: Builder): Flashbar(builder), Bulletin {
    override val name: String = javaClass.name
    override val content: String = builder.message ?: ""
    var options: Options = Options.default()

    override fun show() {
        if (options.ignoreIfSameContentDisplayed && isBulletinWithContentDisplayed(content, name)) return
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

            fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
                options.ignoreIfSameContentDisplayed = ignore
                return this
            }

            fun build() = options
        }

        companion object {
            fun default(): Options = Builder().build()
            fun create(block: Options.() -> Unit) = Options().apply { block() }
        }
    }

    companion object {
        fun create(builder: Builder, block: Options.() -> Unit): BulletinFlashBar {
           return BulletinFlashBar(builder).apply { options = Options().apply { block() } }
        }

        fun create(builder: Builder, options: Options): BulletinFlashBar {
           return BulletinFlashBar(builder).apply { this.options = options }
        }
    }

}