package com.sha.bulletin


import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.dialog.InfoDialog
import com.sha.bulletin.dialog.LoadingDialog
import com.sha.bulletin.dialog.RetryDialog
import com.sha.bulletin.flashbar.StandardFlashbar
import com.sha.bulletin.sheet.InfoSheet
import com.sha.bulletin.sheet.RetrySheet
import com.sha.bulletin.snackbar.StandardSnackbar
import com.sha.bulletin.toast.StandardToast

interface Alertable:
        InfoDialogAlertable,
        InfoSheetAlertable,
        RetryDialogAlertable,
        RetrySheetAlertable,
        ToastAlertable,
        LoadingDialogAlertable,
        FlashBarAlertable,
        SnackbarAlertable,
        DismissAlertable

interface InfoDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_info,
                         @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
        return showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showMessageDialog(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
        return activity()?.run { showMessageDialog(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showWarningDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_warning,
                         @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
        return showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showWarningDialog(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
       return activity()?.run { showWarningDialog(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showErrorDialog(content: String, @DrawableRes iconRes: Int = R.drawable.ic_error,
                       @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
        return showInfoDialog(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showErrorDialog(@StringRes errorRes: Int, @DrawableRes iconRes: Int,
                       @ColorRes iconContainerColorRes: Int = -1): InfoDialog? {
        return activity()?.run { showErrorDialog(getString(errorRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showInfoDialog(
            content: String,
            @DrawableRes iconRes: Int,
            @ColorRes iconContainerColorRes: Int): InfoDialog? {
       return activity()?.let {
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
    fun showInfoDialog(options: InfoDialog.Options = InfoDialog.Options.default()): InfoDialog? {
        return activity()?.run {
            val bulletin = InfoDialog.create(options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface InfoSheetAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_info,
                         @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
        return showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showMessageSheet(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
        return activity()?.run { showMessageSheet(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showWarningSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_warning,
                         @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
        return showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showWarningSheet(@StringRes contentRes: Int, @DrawableRes iconRes: Int,
                         @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
        return activity()?.run { showWarningSheet(getString(contentRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showErrorSheet(content: String, @DrawableRes iconRes: Int = R.drawable.ic_error,
                       @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
        return showInfoSheet(content, iconRes, iconContainerColorRes)
    }

    @JvmDefault
    fun showErrorSheet(@StringRes errorRes: Int, @DrawableRes iconRes: Int,
                       @ColorRes iconContainerColorRes: Int = -1): InfoSheet? {
       return activity()?.run { showErrorSheet(getString(errorRes), iconRes, iconContainerColorRes) }
    }

    @JvmDefault
    fun showInfoSheet(
            content: String,
            @DrawableRes iconRes: Int,
            @ColorRes iconContainerColorRes: Int): InfoSheet? {
      return showInfoSheet(InfoSheet.Options.create {
                this.content = content
                iconSetup = IconSetup.create {
                    this.iconDrawableRes = iconRes
                    containerColorRes = iconContainerColorRes
                }
            })
    }

    @JvmDefault
    fun showInfoSheet(options: InfoSheet.Options = InfoSheet.Options.default()): InfoSheet? {
        return activity()?.run {
            val bulletin = InfoSheet.create(options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface RetryDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showRetryDialog(
            @StringRes contentRes: Int,
            options: RetryDialog.Options = RetryDialog.Options.default()): RetryDialog? {
      return activity()?.run { showRetryDialog(getString(contentRes), options) }
    }

    @JvmDefault
    fun showRetryDialog(
            content: String,
            options: RetryDialog.Options = RetryDialog.Options.default()): RetryDialog? {
        options.content = content
        return activity()?.run {
            val bulletin = RetryDialog.create(options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface RetrySheetAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showRetrySheet(
            @StringRes contentRes: Int,
            options: RetrySheet.Options = RetrySheet.Options.default()): RetrySheet? {
        return activity()?.run { showRetrySheet(getString(contentRes), options) }
    }

    @JvmDefault
    fun showRetrySheet(
            content: String,
            options: RetrySheet.Options = RetrySheet.Options.default()): RetrySheet? {
        options.content = content
        return activity()?.run {
            val bulletin = RetrySheet.create(options)
            showBulletin(bulletin, this)
            bulletin
        }
    }

}

interface ToastAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun longToast(@StringRes content: Int,
                  options: StandardToast.Options = StandardToast.Options.default()): StandardToast? {
        return activity()?.run { toast(getString(content), Toast.LENGTH_LONG, options) }
    }

    @JvmDefault
    fun longToast(content: String,
                  options: StandardToast.Options = StandardToast.Options.default()): StandardToast? {
       return toast(content, Toast.LENGTH_LONG, options)
    }

    @JvmDefault
    fun shortToast(content: String,
                   options: StandardToast.Options = StandardToast.Options.default()): StandardToast? {
       return toast(content, Toast.LENGTH_SHORT, options)
    }

    @JvmDefault
    fun shortToast(@StringRes content: Int,
                   options: StandardToast.Options = StandardToast.Options.default()): StandardToast? {
        return activity()?.run { toast(getString(content), Toast.LENGTH_SHORT, options) }
    }

    @JvmDefault
    fun toast(content: String,
              duration: Int,
              options: StandardToast.Options = StandardToast.Options.default()): StandardToast? {
        return activity()?.run {
            options.content = content
            val bulletin = StandardToast.create(this, options) { this.duration = duration }
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface LoadingDialogAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showLoadingDialog(content: String = ""): LoadingDialog? {
       return showLoadingDialog(LoadingDialog.Options.create { this.content = content })
    }

    @JvmDefault
    fun showLoadingDialog(@StringRes contentRes: Int): LoadingDialog? {
        return activity()?.run {
             showLoadingDialog(LoadingDialog.Options.create { content = getString(contentRes) })
        }
    }

    @JvmDefault
    fun showLoadingDialog(options: LoadingDialog.Options): LoadingDialog? {
        return activity()?.run {
            val bulletin = LoadingDialog.create(options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface FlashBarAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showMessageInFlashBar(content: String,
                              duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return showFlashBar(content, duration, R.color.white)
    }

    @JvmDefault
    fun showMessageInFlashBar(@StringRes contentRes: Int,
                              duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return activity()?.run { showFlashBar(getString(contentRes), duration, R.color.white) }
    }

    @JvmDefault
    fun showWarningInFlashBar(content: String,
                              duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return showFlashBar(content, duration, R.color.warning)
    }

    @JvmDefault
    fun showWarningInFlashBar(@StringRes contentRes: Int,
                              duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return activity()?.run { showFlashBar(getString(contentRes), duration, R.color.warning) }
    }

    @JvmDefault
    fun showErrorInFlashBar(content: String,
                            duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return showFlashBar(content, duration, R.color.error)
    }

    @JvmDefault
    fun showErrorInFlashBar(@StringRes contentRes: Int,
                            duration: Long = BulletinConfig.flashBarDuration): StandardFlashbar? {
        return activity()?.run { showFlashBar(getString(contentRes), duration, R.color.error) }
    }

    @JvmDefault
    fun showFlashBar(content: String, duration: Long,
                     @ColorRes backgroundColor: Int): StandardFlashbar? {
        return activity()?.run {
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
                     options: StandardFlashbar.Options = StandardFlashbar.Options.default()): StandardFlashbar? {
        return activity()?.run {
            val bulletin = StandardFlashbar.create(builder, options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface SnackbarAlertable {
    fun activity(): FragmentActivity?

    @JvmDefault
    fun showSnackabr(coordinatorLayout: View,
                     content: String,
                     duration: Int): StandardSnackbar? {
        return showSnackabr(
                coordinatorLayout,
                duration,
                StandardSnackbar.Options.create { this.content = content })
    }

    @JvmDefault
    fun showSnackabr(coordinatorLayout: View,
                     @StringRes content: Int,
                     duration: Int): StandardSnackbar? {
        return activity()?.run {
            showSnackabr(
                    coordinatorLayout,
                    duration,
                    StandardSnackbar.Options.create { this.content = getString(content) })
        }
    }

    @JvmDefault
    fun showSnackabr(coordinatorLayout: View,
                     duration: Int,
                     options: StandardSnackbar.Options = StandardSnackbar.Options.default()
    ): StandardSnackbar? {
        return activity()?.run {
            val bulletin = StandardSnackbar.create(this, coordinatorLayout, duration, options)
            showBulletin(bulletin, this)
            bulletin
        }
    }
}

interface DismissAlertable {

    fun dismissAllBulletins() {
        BulletinManager.dismissAll()
    }

    fun dismissBulletinWithName(name: String = "") {
        BulletinManager.dismissWithName(name)
    }

    fun dismissLoadingDialogs() {
        BulletinManager.dismissWithName(LoadingDialog::class.java.name)
    }

    fun dismissBulletinWithContent(content: String) {
        BulletinManager.dismissWithContent(content)
    }
}