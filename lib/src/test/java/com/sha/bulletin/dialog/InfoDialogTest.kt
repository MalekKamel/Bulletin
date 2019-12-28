package com.sha.bulletin.dialog

import com.sha.bulletin.IconSetup
import org.junit.Before
import org.junit.Test

class InfoDialogTest {
    lateinit var options: InfoDialog.Options.Builder

    @Before
    fun setup() {
        options = InfoDialog.Options.Builder()
    }

    @Test
    fun title() {
        options.title("title")
        assert(options.build().title == "title")
    }

    @Test
    fun content() {
        options.content("content")
        assert(options.build().content == "content")
    }

    @Test
    fun dismissCallback() {
        options.onDismiss {}
        assert(options.build().onDismiss != null)
    }

    @Test
    fun isCancellable() {
        options.isCancellableOnTouchOutside(false)
        assert(!options.build().isCancellableOnTouchOutside)
    }

    @Test
    fun ignoreIfSameContentDisplayed() {
        options.ignoreIfSameContentDisplayed(true)
        assert(options.build().ignoreIfSameContentDisplayed)
    }

    @Test
    fun iconSetup() {
        options.iconSetup(IconSetup.create {
            iconDrawableRes = 2
            containerColorRes = 3
        })
        assert(options.build().iconSetup.iconDrawableRes == 2)
        assert(options.build().iconSetup.containerColorRes == 3)
    }
}