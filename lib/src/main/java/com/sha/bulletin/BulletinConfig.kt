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
    var duplicateStrategy: DuplicateStrategy = DefaultDuplicateStrategy()
    val queueStrategies = QueueStrategies()
    var iconSetup: IconSetup = IconSetup.default()
    var flashBarDuration = 6000L

    fun queueStrategies(block: QueueStrategies.() -> Unit) = queueStrategies.run { block() }

    operator fun plus(strategy: QueueStrategy) = queueStrategies.add(strategy)

    class Builder {
        /**
         * If true, the bulletin can be cancelled on touch outside
         */
        fun isCancellableOnTouchOutside(cancellable: Boolean): Builder {
            isCancellableOnTouchOutside = cancellable
            return this
        }

        /**
         * [DuplicateStrategy] for managing duplicate bulletins. You can choose
         * one of many implementations in the library or implement your own strategy.
         */
        fun duplicateStrategy(strategy: DuplicateStrategy): Builder {
            duplicateStrategy = strategy
            return this
        }

        /**
         * [QueueStrategy] for managing [Bulletin] queuing
         */
        fun queueStrategies(strategies: Set<QueueStrategy>): Builder {
            queueStrategies.addAll(strategies)
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