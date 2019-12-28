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
        options.onDismissClicked {}
        assert(options.build().onDismissClicked != null)
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }

    @Test
    fun isCancellable() {
        options.isCancellableOnTouchOutside(false)
        assert(!options.build().isCancellableOnTouchOutside)
    }
}