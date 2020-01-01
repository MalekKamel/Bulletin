package com.sha.bulletin.dialog

import com.sha.bulletin.DefaultDuplicateStrategy
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
    fun setupContentTextView() {
        options.setupContentTextView {}
        assert(options.build().setupContentTextView != null)
    }

    @Test
    fun dismissCallback() {
        options.onDismissClicked {}
        assert(options.build().onDismissClicked != null)
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        val strategy = DefaultDuplicateStrategy()
        options.duplicateStrategy(strategy)
        assert(options.build().duplicateStrategy == strategy)
    }

    @Test
    fun isCancellable() {
        options.isCancellableOnTouchOutside(false)
        assert(!options.build().isCancellableOnTouchOutside)
    }
}