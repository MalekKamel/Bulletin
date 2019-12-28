package com.sha.bulletin

data class IconSetup(
        var iconDrawableRes: Int = -1,
        var containerColorRes: Int = R.color.blue
) {
    class Builder {
        private val setup = IconSetup()

        /**
         * Drawable resource of the icon
         */
        fun iconDrawableRes(res: Int): Builder {
            setup.iconDrawableRes = res
            return this
        }

        /**
         * Color of the icon container
         */
        fun containerColorRes(res: Int): Builder {
            setup.containerColorRes = res
            return this
        }

        /**
         * Returns an instance of this setup
         */
        fun build() = setup
    }

    companion object {
        /**
         * Create the default setup
         */
        fun default(): IconSetup = Builder().build()

        /**
         * Create the setup
         * @param block DSL for creating the setup
         */
        fun create(block: IconSetup.() -> Unit) = IconSetup().apply { block() }
    }
}