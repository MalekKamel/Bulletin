package com.sha.bulletin.toast

import org.junit.Before
import org.junit.Test

class StandardBulletinToastTest {
    lateinit var options: StandardBulletinToast.Options.Builder

    @Before
    fun setup() {
        options = StandardBulletinToast.Options.Builder()
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