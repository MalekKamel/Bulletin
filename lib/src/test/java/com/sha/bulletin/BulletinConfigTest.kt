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
        options.isCancellable(false)
        assert(!options.build().isCancellable)
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }

    @Test
    fun iconSetup() {
        options.iconSetup(IconSetup.create {
            iconRes = 2
            containerColorRes = 3
        })
        assert(options.build().iconSetup.iconRes == 2)
        assert(options.build().iconSetup.containerColorRes == 3)
    }
}