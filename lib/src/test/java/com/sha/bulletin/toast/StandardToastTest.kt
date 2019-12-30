package com.sha.bulletin.toast

import com.sha.bulletin.DefaultDuplicateStrategy
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
        val strategy = DefaultDuplicateStrategy()
        options.duplicateStrategy(strategy)
        assert(options.build().duplicateStrategy == strategy)
    }
}