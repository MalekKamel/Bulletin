package com.sha.bulletin.flashbar

import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.Bulletin
import com.sha.bulletin.bulletins
import com.sha.bulletin.isBulletinDisplayed

abstract class BulletinFlashBar(builder: Builder): Flashbar(builder), Bulletin {
    abstract var ignoreIfSameContentDisplayed: Boolean

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (ignoreIfSameContentDisplayed && isBulletinDisplayed(name, content)) return
        super.show()
        bulletins.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        bulletins.remove(this)
    }
}