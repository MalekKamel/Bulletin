package com.sha.bulletin.toast

import android.content.Context
import android.os.Handler
import android.widget.Toast
import com.sha.bulletin.*
import java.util.concurrent.TimeUnit

abstract class BulletinToast(context: Context): Toast(context), Bulletin {
    abstract var duplicateStrategy: DuplicateStrategy

    override fun dismiss() {
        cancel()
        BulletinManager.remove(this)
    }

    /**
     * Show this [Bulletin]
     */
    override fun show() {
        if (duplicateStrategy.shouldIgnore(this, BulletinManager.bulletins)) return
        BulletinManager.add(this)
        super.show()
        // schedule removing from bulletins
        val duration = if(duration == LENGTH_LONG) 3.5 else 2.toDouble()
        Handler().postDelayed({ BulletinManager.remove(this) },  TimeUnit.SECONDS.toMillis(duration.toLong()) )
    }

}