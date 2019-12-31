package com.sha.bulletin


import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.dialog.InfoDialog
import com.sha.bulletin.dialog.LoadingDialog
import com.sha.bulletin.dialog.RetryDialog
import com.sha.bulletin.flashbar.StandardFlashBar
import com.sha.bulletin.sheet.InfoSheet
import com.sha.bulletin.sheet.RetrySheet
import com.sha.bulletin.toast.StandardToast

interface Alertable:
        InfoDialogAlertable,
        InfoSheetAlertable,
        RetryDialogAlertable,
        RetrySheetAlertable,
        ToastAlertable,
        LoadingDialogAlertable,
        FlashBarAlertable

interface InfoDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_info,
                         @ColorRes iconContainerColorRes: Int = -1) {
        showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showMessageDialog(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showMessageDialog(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showWarningDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_warning,
                         @ColorRes iconContainerColorRes: Int = -1) {
        showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showWarningDialog(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showWarningDialog(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showErrorDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_error,
                       @ColorRes iconContainerColorRes: Int = -1) {
        showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showErrorDialog(@StringRes errorRes: Int, @DrawableRes iconRes: Int,
                       @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showErrorDialog(getString(errorRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showInfoDialog(
            content: String,
            @DrawableRes iconRes: Int,
            @ColorRes iconContainerColorRes: Int) {
        activity()?.let {
            showInfoDialog(InfoDialog.Options.create {
                this.content = content
                iconSetup = IconSetup.create {
                    this.iconDrawableRes = iconRes
                    containerColorRes = iconContainerColorRes
                }
            })
        }
    }

    @JvmDefault
    fun showInfoDialog(options: InfoDialog.Options = InfoDialog.Options.default()) {
        activity()?.run {
            BulletinManager.show(InfoDialog.create(options), this, options.duplicateStrategy)
        }
    }
}

interface InfoSheetAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_info,
                         @ColorRes iconContainerColorRes: Int = -1) {
        showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showMessageSheet(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showMessageSheet(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showWarningSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_warning,
                         @ColorRes iconContainerColorRes: Int = -1) {
        showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showWarningSheet(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showWarningSheet(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showErrorSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_error,
                       @ColorRes iconContainerColorRes: Int = -1) {
        showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showErrorSheet(@StringRes errorRes: Int, @DrawableRes iconRes: Int,
                       @ColorRes iconContainerColorRes: Int = -1) {
        activity()?.run { showErrorSheet(getString(errorRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showInfoSheet(
            content: String,
            @DrawableRes iconRes: Int,
            @ColorRes iconContainerColorRes: Int) {
        activity()?.let {
            showInfoSheet(InfoSheet.Options.create {
                this.content = content
                iconSetup = IconSetup.create {
                    this.iconDrawableRes = iconRes
                    containerColorRes = iconContainerColorRes
                }
            })
        }
    }

    @JvmDefault
    fun showInfoSheet(options: InfoSheet.Options = InfoSheet.Options.default()) {
        activity()?.run {
            BulletinManager.show(InfoSheet.create(options), this, options.duplicateStrategy)
        }
    }
}

interface RetryDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showRetryDialog(
            @StringRes contentRes: Int,
            options: RetryDialog.Options = RetryDialog.Options.default()) {
        activity()?.run { showRetryDialog(getString(contentRes), options) }
    }

    @JvmDefault
    fun showRetryDialog(
            content: String,
            options: RetryDialog.Options = RetryDialog.Options.default()) {
        options.content = content
        activity()?.run {
            BulletinManager.show(RetryDialog.create(options), this, options.duplicateStrategy)
        }
    }
}

interface RetrySheetAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showRetrySheet(
            @StringRes contentRes: Int,
            options: RetrySheet.Options = RetrySheet.Options.default()) {
        activity()?.run { showRetrySheet(getString(contentRes), options) }
    }

    @JvmDefault
    fun showRetrySheet(
            content: String,
            options: RetrySheet.Options = RetrySheet.Options.default()) {
        options.content = content
        activity()?.run {
            BulletinManager.show(RetrySheet.create(options), this, options.duplicateStrategy)
        }
    }

}

interface ToastAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun longToast(@StringRes content: Int,
                  options: StandardToast.Options = StandardToast.Options.default()) {
        activity()?.run { toast(getString(content), Toast.LENGTH_LONG, options) }
    }

    @JvmDefault
    fun longToast(content: String,
                  options: StandardToast.Options = StandardToast.Options.default()) {
        toast(content, Toast.LENGTH_LONG, options)
    }

    @JvmDefault
    fun shortToast(content: String,
                   options: StandardToast.Options = StandardToast.Options.default()) {
        toast(content, Toast.LENGTH_SHORT, options)
    }

    @JvmDefault
    fun shortToast(@StringRes content: Int,
                   options: StandardToast.Options = StandardToast.Options.default()) {
        activity()?.run { toast(getString(content), Toast.LENGTH_SHORT, options) }
    }

    @JvmDefault
    fun toast(content: String,
              duration: Int,
              options: StandardToast.Options = StandardToast.Options.default()) {
        activity()?.run {
            options.content = content
            val toast = StandardToast.create(this, options) { this.duration = duration }
            BulletinManager.show(toast,this, options.duplicateStrategy)
        }
    }
}

interface LoadingDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showLoadingDialog(content: String = "") {
        showLoadingDialog(LoadingDialog.Options.create { this.content = content })
    }

    @JvmDefault
    fun showLoadingDialog(@StringRes contentRes: Int) {
        activity()?.run {
            showLoadingDialog(LoadingDialog.Options.create { content = getString(contentRes) })
        }
    }

    @JvmDefault
    fun showLoadingDialog(options: LoadingDialog.Options = LoadingDialog.Options.default()) {
        activity()?.run {
            BulletinManager.show(LoadingDialog.create(options), this, options.duplicateStrategy)
        }
    }
}

interface FlashBarAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageInFlashBar(content: String, duration: Long = BulletinConfig.flashBarDuration) {
        showFlashBar(content, duration, R.color.white)
    }

    @JvmDefault
    fun showMessageInFlashBar(@StringRes contentRes: Int, duration: Long = BulletinConfig.flashBarDuration) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.white) }
    }

    @JvmDefault
    fun showWarningInFlashBar(content: String, duration: Long = BulletinConfig.flashBarDuration) {
        showFlashBar(content, duration, R.color.warning)
    }

    @JvmDefault
    fun showWarningInFlashBar(@StringRes contentRes: Int, duration: Long = BulletinConfig.flashBarDuration) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.warning) }
    }

    @JvmDefault
    fun showErrorInFlashBar(content: String, duration: Long = BulletinConfig.flashBarDuration) {
        showFlashBar(content, duration, R.color.error)
    }

    @JvmDefault
    fun showErrorInFlashBar(@StringRes contentRes: Int, duration: Long = BulletinConfig.flashBarDuration) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.error) }
    }

    @JvmDefault
    fun showFlashBar(content: String, duration: Long, @ColorRes backgroundColor: Int) {
        activity()?.run {
           val builder = Flashbar.Builder(this)
                    .message(content)
                    .gravity(Flashbar.Gravity.TOP)
                    .duration(duration)
                    .enableSwipeToDismiss()
                    .backgroundColorRes(backgroundColor)
            showFlashBar(builder)
        }
    }

    @JvmDefault
    fun showFlashBar(builder: Flashbar.Builder,
                     options: StandardFlashBar.Options = StandardFlashBar.Options.default()) {
        activity()?.run {
            BulletinManager.show(StandardFlashBar.create(builder, options), this, options.duplicateStrategy)
        }
    }
}