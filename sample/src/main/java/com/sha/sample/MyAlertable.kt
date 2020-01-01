package com.sha.sample

import androidx.annotation.StringRes
import com.sha.bulletin.Alertable
import com.sha.bulletin.BulletinManager

interface MyAlertable: Alertable, CustomLoadingDialogAlertable

interface CustomLoadingDialogAlertable: Alertable {

    @JvmDefault
    fun showCustomLoadingDialog(content: String = "") {
        showCustomLoadingDialog(MyCustomLoadingDialog.Options.create { this.content = content })
    }

    @JvmDefault
    fun showCustomLoadingDialog(@StringRes contentRes: Int) {
        activity()?.run {
            showCustomLoadingDialog(MyCustomLoadingDialog.Options.create { content = getString(contentRes) })
        }
    }

    @JvmDefault
    fun showCustomLoadingDialog(options: MyCustomLoadingDialog.Options = MyCustomLoadingDialog.Options.default()) {
        activity()?.run {
            BulletinManager.show(MyCustomLoadingDialog.create(options), this, options.duplicateStrategy)
        }
    }
}