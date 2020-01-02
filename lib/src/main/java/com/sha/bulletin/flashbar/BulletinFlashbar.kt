package com.sha.bulletin.flashbar

import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.*

abstract class BulletinFlashbar(val builder: Builder): Flashbar(builder), Bulletin {

    override var status: BulletinStatus = BulletinStatus.PENDING

    override fun showBulletin(activity: FragmentActivity?){
        builder.build(this).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        BulletinManager.onDismiss(this, builder.activity)
    }
}