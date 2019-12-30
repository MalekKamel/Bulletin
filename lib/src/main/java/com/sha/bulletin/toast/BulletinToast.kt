package com.sha.bulletin.toast

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinManager
import com.sha.bulletin.isDisplayed
import java.util.concurrent.TimeUnit

abstract class BulletinToast(context: Context): Toast(context), Bulletin {
    abstract var ignoreIfSameContentDisplayed: Boolean

    override fun dismiss() {
        cancel()
        BulletinManager.remove(this)
    }

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (ignoreIfSameContentDisplayed && isDisplayed(name, content)) return
        super.show()
        BulletinManager.add(this)

        // schedule removing from bulletins
        val duration = if(duration == LENGTH_LONG) 3.5 else 2.toDouble()
        Handler().postDelayed({ BulletinManager.remove(this) },  TimeUnit.SECONDS.toMillis(duration.toLong()) )
    }

}