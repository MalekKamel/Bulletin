package com.sha.bulletin.snackbar

import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sha.bulletin.Bulletin
import com.sha.bulletin.BulletinManager
import com.sha.bulletin.BulletinStatus


abstract class BulletinSnackbar(val activity: FragmentActivity,
                                coordinatorLayout: View,
                                text: String,
                                duration: Int): Bulletin {
    override var status: BulletinStatus = BulletinStatus.PENDING
    private var onShown: (() -> Unit)? = null
    private var onDismiss: (() -> Unit)? = null
    abstract fun setup(snackbar: Snackbar)

    var snackbar: Snackbar = Snackbar
            .make(coordinatorLayout, text, duration)

    init {
        snackbar.addCallback(object: Snackbar.Callback() {
            override fun onShown(sb: Snackbar?) {
                super.onShown(sb)
                onShown?.invoke()
            }
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                BulletinManager.onDismiss(this@BulletinSnackbar, activity)
                onDismiss?.invoke()
                onDismiss()
            }
        })
    }
    
    override fun showBulletin(activity: FragmentActivity?) {
        setup(snackbar)
        snackbar.show()
    }

    open fun onDismiss() {}

    fun onDismissed(callback: () -> Unit) {
        this.onDismiss = callback
    }

    fun onShown(callback: () -> Unit) {
        this.onDismiss = callback
    }

    override fun dismiss() {
        snackbar.dismiss()
    }
}