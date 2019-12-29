package com.sha.bulletin.toast

import org.junit.Before
import org.junit.Test

class StandardToastTest {
    lateinit var options: StandardToast.Options.Builder

    @Before
    fun setup() {
        options = StandardToast.Options.Builder()
    }

    @Test
    fun content() {
        options.content("content")
        assert(options.build().content == "content")
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }
}