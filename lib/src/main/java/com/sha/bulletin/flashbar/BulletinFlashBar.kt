package com.sha.bulletin.flashbar

import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.*

abstract class BulletinFlashBar(val builder: Builder): Flashbar(builder), Bulletin {
    abstract var duplicateStrategy: DuplicateStrategy

    override fun showBulletin(activity: FragmentActivity?){
        builder.build(this).show()
    }

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        BulletinManager.add(this)
        super.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        BulletinManager.remove(this, builder.activity)
    }
}