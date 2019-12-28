package com.sha.bulletin

/**
 * Global configuration for any [Bulletin]
 * Keep in mind that these are the default configuration for any Bulletin and can be overridden
 * by options of the any [Bulletin].
 * for example, setting [BulletinConfig.isCancellableOnTouchOutside] to true will be applied to each
 * [Bulletin]. But if it's set to false in, for example, [com.sha.bulletin.dialog.InfoDialog.Options]
 * it will be false for this instance.
 */
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