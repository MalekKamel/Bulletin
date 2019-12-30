package com.sha.bulletin.flashbar

import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.*

abstract class BulletinFlashBar(builder: Builder): Flashbar(builder), Bulletin {
    abstract var duplicateStrategy: DuplicateStrategy

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (duplicateStrategy.shouldIgnore(this, BulletinManager.bulletins)) return
        BulletinManager.add(this)
        super.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        BulletinManager.remove(this)
    }
}