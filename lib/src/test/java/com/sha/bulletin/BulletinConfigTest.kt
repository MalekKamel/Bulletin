package com.sha.bulletin

import org.junit.Before
import org.junit.Test

class BulletinConfigTest {
    lateinit var options: BulletinConfig.Builder

    @Before
    fun setup() {
        options = BulletinConfig.Builder()
    }

    @Test
    fun isCancellable() {
        options.isCancellableOnTouchOutside(false)
        assert(!options.build().isCancellableOnTouchOutside)
    }

    @Test
    fun queueStrategies() {
        val strategy = DialogQueueStrategy()
        options.queueStrategies(setOf(strategy))
        assert(options.build().queueStrategies.size == 1 && options.build().queueStrategies.first() == strategy)
    }

    @Test
    fun duplicateStrategy() {
        val strategy = DefaultDuplicateStrategy()
        options.duplicateStrategy(strategy)
        assert(options.build().duplicateStrategy == strategy)
    }

    @Test
    fun iconSetup() {
        options.iconSetup(IconSetup.create {
            iconDrawableRes = 2
            containerColorRes = 3
        })
        assert(options.build().iconSetup.iconDrawableRes == 2)
        assert(options.build().iconSetup.containerColorRes == 3)
    }
}