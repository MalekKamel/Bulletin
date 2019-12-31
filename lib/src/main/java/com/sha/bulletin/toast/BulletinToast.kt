package com.sha.bulletin.toast

import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinManager
import com.sha.bulletin.DuplicateStrategy
import java.util.concurrent.TimeUnit

abstract class BulletinToast(private val activity: FragmentActivity): Toast(activity), Bulletin {
    abstract var duplicateStrategy: DuplicateStrategy

    override fun dismiss() {
        cancel()
        BulletinManager.removeFromDisplayed(this, activity)
    }

    override fun showBulletin(activity: FragmentActivity?) = show()
    /**
     * Show this [Bulletin]
     */
    override fun show() {
        BulletinManager.addToDisplayed(this)
        super.show()
        // schedule removing from bulletins
        val duration = if(duration == LENGTH_LONG) 3.5 else 2.toDouble()
        Handler().postDelayed({ BulletinManager.removeFromDisplayed(this, activity) },  TimeUnit.SECONDS.toMillis(duration.toLong()) )
    }

}