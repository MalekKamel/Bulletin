package com.sha.bulletin

import com.sha.bulletin.flashbar.BulletinFlashBar
import org.junit.Before
import org.junit.Test

class BulletinFlashBarTest {
    lateinit var options: BulletinFlashBar.Options.Builder

    @Before
    fun setup() {
        options = BulletinFlashBar.Options.Builder()
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }
}