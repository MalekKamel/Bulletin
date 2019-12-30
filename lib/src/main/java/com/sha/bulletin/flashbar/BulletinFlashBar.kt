package com.sha.bulletin.flashbar

import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.*

abstract class BulletinFlashBar(builder: Builder): Flashbar(builder), Bulletin {
    abstract var duplicateStrategy: DuplicateStrategy

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (duplicateStrategy.shouldIgnore(this, bulletins)) return
        super.show()
        BulletinManager.add(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        BulletinManager.remove(this)
    }
}