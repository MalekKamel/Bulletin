package com.sha.bulletin


import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.dialog.InfoDialog
import com.sha.bulletin.dialog.LoadingDialog
import com.sha.bulletin.dialog.RetryDialog
import com.sha.bulletin.sheet.InfoSheet
import com.sha.bulletin.sheet.RetrySheet

interface Alertable:
        InfoDialogAlertable,
        InfoSheetAlertable,
        RetryDialogAlertable,
        RetrySheetAlertable,
        ToastAlertable,
        FlashBarAlertable,
        LoadingDialogAlertable

interface InfoDialogAlertable {
    fun activity(): FragmentActivity?

    fun showMessageDialog(content: String?) {
        showInfoDialog(InfoDialog.Options.create(ContentType.INFO) { this.content = content })
    }

    fun showMessageDialog(@StringRes contentRes: Int) {
        showInfoDialog(InfoDialog.Options.create(ContentType.INFO) {
            content = activity()?.getString(contentRes)
        })
    }

    fun showWarningDialog(content: String?) {
        showInfoDialog(InfoDialog.Options.create(ContentType.WARNING) { this.content = content })
    }

    fun showWarningDialog(contentRes: Int) = showWarningDialog(activity()?.getString(contentRes))

    fun showErrorDialog(@StringRes errorRes: Int) = showErrorDialog(activity()?.getString(errorRes))

    fun showErrorDialog(error: String?) {
        showInfoDialog(InfoDialog.Options.create(ContentType.ERROR) { this.content = error })
    }

    fun showInfoDialog(options: InfoDialog.Options = InfoDialog.Options.defaultOptions()) {
        activity()?.let { InfoDialog.create(options).show(it) }
    }
}

interface LoadingDialogAlertable {
    fun activity(): FragmentActivity?

    fun showLoadingDialog(content: String? = null) {
        showLoadingDialog(LoadingDialog.Options.create { this.content = content })
    }

    fun showLoadingDialog(@StringRes contentRes: Int) {
        showLoadingDialog(LoadingDialog.Options.create { content = activity()?.getString(contentRes) })
    }

    fun showLoadingDialog(options: LoadingDialog.Options = LoadingDialog.Options.defaultOptions()) {
        activity()?.let { LoadingDialog.create(options).show(it) }
    }
}

interface InfoSheetAlertable {
    fun activity(): FragmentActivity?

    fun showMessageSheet(content: String?) {
        showInfoSheet(InfoSheet.Options.create(ContentType.INFO) { this.content = content })
    }

    fun showMessageSheet(@StringRes contentRes: Int) {
        showInfoSheet(InfoSheet.Options.create(ContentType.INFO) {
            content = activity()?.getString(contentRes)
        })
    }

    fun showWarningSheet(content: String?) {
        showInfoSheet(InfoSheet.Options.create(ContentType.WARNING) { this.content = content })
    }

    fun showWarningSheet(@StringRes contentRes: Int) = showWarningSheet(activity()?.getString(contentRes))

    fun showErrorSheet(@StringRes errorRes: Int) = showErrorSheet(activity()?.getString(errorRes))

    fun showErrorSheet(error: String?) {
        showInfoSheet(InfoSheet.Options.create(ContentType.ERROR) { content = error })
    }

    private fun showInfoSheet(options: InfoSheet.Options = InfoSheet.Options.defaultOptions()) {
        activity()?.let { InfoSheet.create(options).show(it) }
    }
}

interface RetryDialogAlertable {
    fun activity(): FragmentActivity?

    fun showRetryDialog(
            @StringRes contentRes: Int,
            options: RetryDialog.Options = RetryDialog.Options.defaultOptions()) {
        activity()?.run { showRetryDialog(getString(contentRes), options) }
    }

    fun showRetryDialog(
            content: String,
            options: RetryDialog.Options = RetryDialog.Options.defaultOptions()) {
        options.content = content
        activity()?.let { RetryDialog.create(options).show(it) }
    }
}

interface RetrySheetAlertable {
    fun activity(): FragmentActivity?

    fun showRetrySheet(
            @StringRes contentRes: Int,
            options: RetrySheet.Options = RetrySheet.Options.defaultOptions()) {
        activity()?.run { showRetrySheet(getString(contentRes), options) }
    }

    fun showRetrySheet(
            content: String,
            options: RetrySheet.Options = RetrySheet.Options.defaultOptions()) {
        options.content = content
        activity()?.let { RetrySheet.create(options).show(it) }
    }

}

interface ToastAlertable {
    fun activity(): FragmentActivity?

    fun longToast(@StringRes content: Int) {
        Toast.makeText(activity(), content, Toast.LENGTH_LONG).show()
    }

    fun longToast(content: String) {
        Toast.makeText(activity(), content, Toast.LENGTH_LONG).show()
    }

    fun shortToast(content: String) {
        Toast.makeText(activity(), content, Toast.LENGTH_SHORT).show()
    }

    fun shortToast(@StringRes content: Int) {
        Toast.makeText(activity(), content, Toast.LENGTH_SHORT).show()
    }
}

interface FlashBarAlertable {
    fun activity(): FragmentActivity?

    fun showMessageInFlashBar(content: String, duration: Long = 6000) {
        activity()?.run { showFlashBar(content, duration, R.color.white) }
    }

    fun showMessageInFlashBar(@StringRes contentRes: Int, duration: Long = 6000) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.white) }
    }

    fun showWarningInFlashBar(content: String, duration: Long = 6000) {
        activity()?.run { showFlashBar(content, duration, R.color.warning) }
    }

    fun showWarningInFlashBar(@StringRes contentRes: Int, duration: Long = 6000) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.warning) }
    }

    fun showErrorInFlashBar(content: String, duration: Long = 6000) {
        activity()?.run { showFlashBar(content, duration, R.color.exception) }
    }

    fun showErrorInFlashBar(@StringRes contentRes: Int, duration: Long = 6000) {
        activity()?.run { showFlashBar(getString(contentRes), duration, R.color.exception) }
    }

    private fun showFlashBar(content: String, duration: Long, @ColorRes backgroundColor: Int) {
        activity()?.run {
            showFlashBar(
                    Flashbar.Builder(this)
                            .message(content)
                            .gravity(Flashbar.Gravity.TOP)
                            .duration(duration)
                            .dismissOnTapOutside()
                            .enableSwipeToDismiss()
                            .backgroundColorRes(backgroundColor))
        }
    }

    fun showFlashBar(builder: Flashbar.Builder) = activity()?.run { builder.build().show() }
}