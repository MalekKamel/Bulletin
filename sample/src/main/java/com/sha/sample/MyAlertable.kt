package com.sha.sample

import androidx.annotation.StringRes
import com.sha.bulletin.Alertable
import com.sha.bulletin.BulletinManager

interface MyAlertable: Alertable, CustomLoadingDialogAlertable

interface CustomLoadingDialogAlertable: Alertable {

    @JvmDefault
    fun showCustomLoadingDialog(content: String = ""): MyCustomLoadingDialog? {
       return showCustomLoadingDialog(MyCustomLoadingDialog.Options.create { this.content = content })
    }

    @JvmDefault
    fun showCustomLoadingDialog(@StringRes contentRes: Int): MyCustomLoadingDialog? {
       return activity()?.run {
            showCustomLoadingDialog(MyCustomLoadingDialog.Options.create { content = getString(contentRes) })
        }
    }

    @JvmDefault
    fun showCustomLoadingDialog(options: MyCustomLoadingDialog.Options = MyCustomLoadingDialog.Options.default()): MyCustomLoadingDialog? {
        return activity()?.run {
            val bulletin = MyCustomLoadingDialog.create(options)
            BulletinManager.show(bulletin, this, options.duplicateStrategy)
            bulletin
        }
    }
}