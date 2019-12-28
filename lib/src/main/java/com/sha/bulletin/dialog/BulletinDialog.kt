package com.sha.bulletin.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.annotation.Nullable
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Bulletin
import com.sha.bulletin.bulletins
import com.sha.bulletin.isBulletinDisplayed

/**
 * Created by Sha on 12/24/19.
 */

/**
 * [DialogFragment] implements [Bulletin]
 */
abstract class BulletinDialog : DialogFragment(), Bulletin {
    protected open var transparentWindow: Boolean  = true
    protected open var isCanceledOnTouchOutside: Boolean = false
    private var onDismissListener: (() -> Unit)? = null
    abstract var layoutId: Int
    open var isDisplayed: Boolean = false
    open var hasTitle: Boolean = false

    abstract var ignoreIfSameContentDisplayed: Boolean

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setCanceledOnTouchOutside(isCanceledOnTouchOutside)
    }

    @Nullable
    override fun onCreateView(
            inflater: LayoutInflater,
            @Nullable container: ViewGroup?,
            @Nullable savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutId, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val w = dialog.window
        // Hide title
        if(!hasTitle)
            w?.requestFeature(Window.FEATURE_NO_TITLE)
        if (transparentWindow) w?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    /**
     * Show this bulletin
     */
    open fun show(activity: FragmentActivity) {
        if (ignoreIfSameContentDisplayed && isBulletinDisplayed(name, content)) return
        if (isDisplayed) return
        show(activity.supportFragmentManager, name)
        isDisplayed = true
        bulletins.add(this)
    }

    fun onDismissListener(callback: () -> Unit): BulletinDialog {
        this.onDismissListener = callback
        return this
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        onDismissListener?.invoke()
        isDisplayed = false
        bulletins.remove(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        isDisplayed = false
        bulletins.remove(this)
    }
}
