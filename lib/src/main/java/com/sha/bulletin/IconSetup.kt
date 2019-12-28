package com.sha.bulletin

data class IconSetup(
        var iconRes: Int = -1,
        var containerColorRes: Int = R.color.blue
) {
    class Builder {
        private val setup = IconSetup()

        fun iconRes(res: Int): Builder {
            setup.iconRes = res
            return this
        }

        fun containerColorRes(res: Int): Builder {
            setup.containerColorRes = res
            return this
        }
        fun build() = setup
    }

    companion object {
        fun default(): IconSetup = Builder().build()
        fun create(block: IconSetup.() -> Unit) = IconSetup().apply { block() }
    }
}