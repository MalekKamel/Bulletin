package com.sha.bulletin.snackbar

import com.sha.bulletin.DefaultDuplicateStrategy
import com.sha.bulletin.IconSetup
import org.junit.Before
import org.junit.Test

class BulletinSnackbarTest {
    lateinit var options: StandardSnackbar.Options.Builder

    @Before
    fun setup() {
        options = StandardSnackbar.Options.Builder()
    }

    @Test
    fun content() {
        options.content("content")
        assert(options.build().content == "content")
    }

    @Test
    fun testSetup() {
        options.setup {}
        assert(options.build().setup != null)
    }

    @Test
    fun onDismiss() {
        options.onDismiss {}
        assert(options.build().onDismiss != null)
    }

    @Test
    fun duplicateStrategy() {
        val strategy = DefaultDuplicateStrategy()
        options.duplicateStrategy(strategy)
        assert(options.build().duplicateStrategy == strategy)
    }

}