package com.sha.bulletin.toast

import android.os.Handler
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinManager
import com.sha.bulletin.BulletinStatus
import java.util.concurrent.TimeUnit

abstract class BulletinToast(private val activity: FragmentActivity): Toast(activity), Bulletin {
    override var status: BulletinStatus = BulletinStatus.PENDING

    override fun dismiss() {
        cancel()
        BulletinManager.onDismiss(this, activity)
    }

    override fun showBulletin(activity: FragmentActivity?) = show()
    /**
     * Show this [Bulletin]
     */
    override fun show() {
        super.show()
        // schedule removing from bulletins
        val duration = if(duration == LENGTH_LONG) 3.5 else 2.toDouble()
        Handler().postDelayed(
                { BulletinManager.onDismiss(this, activity) },
                TimeUnit.SECONDS.toMillis(duration.toLong()) )
    }

}