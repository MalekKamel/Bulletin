package com.sha.bulletin

var bulletins: MutableList<Bulletin> = mutableListOf()

interface Bulletin {
    val name: String
    val content: String
    fun dismiss()
}

/**
 * Returns true if the bulletin is displayed
 */
fun isBulletinDisplayed(bulletin: Bulletin) = bulletins.any { it == bulletin }

/**
 * Returns true if the bulletin is displayed
 * @param name of the bulletin
 */
fun isBulletinDisplayed(name: String) = bulletins.any { it.name == name }

fun isBulletinDisplayed(name: String, content: String): Boolean {
   return bulletins.filter { it.name == name }.any { it.content == content }
}

/**
 * Dismiss all bulletins
 */
fun dismissAllBulletins() { bulletins.forEach { it.dismiss() } }

/**
 * Dismiss Bulletin with its name
 * @param name of the bulletin
 */
fun dismissBulletinWithName(name: String) {
    bulletins.filter { it.name == name }.forEach { it.dismiss() }
}

/**
 * Dismiss Bulletin with its content
 * @param content of the bulletin
 */
fun dismissBulletinWithContent(content: String) {
    bulletins.filter { it.content == content }.forEach { it.dismiss() }
}

/**
 * Dismiss Bulletin with its content
 * @param name of the bulletin
 * @param content of the bulletin
 */
fun dismissBulletin(name: String, content: String) {
    bulletins.filter { it.name == name && it.content == content }.forEach { it.dismiss() }
}

object BulletinConfig {
    var isCancellableOnTouchOutside: Boolean = true
    var ignoreIfSameContentDisplayed: Boolean = true
    var iconSetup: IconSetup = IconSetup.default()

    class Builder {

        /**
         * If true, the bulletin can be cancelled on touch outside
         */
        fun isCancellableOnTouchOutside(cancellable: Boolean): Builder {
            isCancellableOnTouchOutside = cancellable
            return this
        }

        /**
         * If true, this bulletin won't be displayed if there's another bulletin displayed
         * with the same name and content of this bulletin
         */
        fun ignoreIfSameContentDisplayed(ignore: Boolean): Builder {
            ignoreIfSameContentDisplayed = ignore
            return this
        }

        /**
         * Setup icon
         */
        fun iconSetup(setup: IconSetup): Builder {
            iconSetup = setup
            return this
        }

        /**
         * Returns the instance
         */
        fun build() = BulletinConfig
    }

    /**
     * Create the default config
     */
    fun default(): BulletinConfig = Builder().build()
    /**
     * Create the options
     * @param block DSL for creating the config
     */
    fun create(block: BulletinConfig.() -> Unit) = BulletinConfig.apply { block() }
}