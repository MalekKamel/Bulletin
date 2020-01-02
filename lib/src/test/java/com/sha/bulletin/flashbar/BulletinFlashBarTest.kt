package com.sha.bulletin.flashbar

import com.sha.bulletin.DefaultDuplicateStrategy
import org.junit.Before
import org.junit.Test

class StandardFlashBarTest {
    lateinit var options: StandardFlashbar.Options.Builder

    @Before
    fun setup() {
        options = StandardFlashbar.Options.Builder()
    }

    @Test
    fun duplicateStrategy() {
        val strategy = DefaultDuplicateStrategy()
        options.duplicateStrategy(strategy)
        assert(options.build().duplicateStrategy == strategy)
    }
}