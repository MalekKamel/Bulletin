package com.sha.bulletin.flashbar

import org.junit.Before
import org.junit.Test

class StandardFlashBarTest {
    lateinit var options: StandardFlashBar.Options.Builder

    @Before
    fun setup() {
        options = StandardFlashBar.Options.Builder()
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }
}