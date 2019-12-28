package com.sha.bulletin.dialog

import org.junit.Before
import org.junit.Test

class LoadingDialogTest {
    lateinit var options: LoadingDialog.Options.Builder

    @Before
    fun setup() {
        options = LoadingDialog.Options.Builder()
    }

    @Test
    fun content() {
        options.content("content")
        assert(options.build().content == "content")
    }

    @Test
    fun dismissCallback() {
        options.dismissCallback {}
        assert(options.build().dismissCallback != null)
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }

    @Test
    fun isCancellable() {
        options.isCancellable(false)
        assert(!options.build().isCancellable)
    }
}