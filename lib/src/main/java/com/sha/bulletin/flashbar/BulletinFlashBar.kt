package com.sha.bulletin.flashbar

import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinManager
import com.sha.bulletin.isDisplayed

abstract class BulletinFlashBar(builder: Builder): Flashbar(builder), Bulletin {
    abstract var ignoreIfSameContentDisplayed: Boolean

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (ignoreIfSameContentDisplayed && isDisplayed(name, content)) return
        super.show()
        BulletinManager.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BulletinManager.remove(this)
    }
}