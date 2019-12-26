package com.sha.bulletin


import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.FragmentActivity
import com.andrognito.flashbar.Flashbar
import com.sha.bulletin.dialog.InfoDialog
import com.sha.bulletin.dialog.RetryDialog
import com.sha.bulletin.sheet.InfoSheet
import com.sha.bulletin.sheet.RetrySheet

interface Alertable:
        InfoDialogAlertable,
        InfoSheetAlertable,
        RetryDialogAlertable,
        RetrySheetAlertable,
        ToastAlertable,
        FlashBarAlertable

interface InfoDialogAlertable {
    fun activity(): FragmentActivity?

    fun showMessageDialog(msg: String?) {
        showInfoDialog(InfoDialog.Options.create(MessageType.INFO) { message = msg })
    }

    fun showMessageDialog(@StringRes msgRes: Int) {
        showInfoDialog(InfoDialog.Options.create(MessageType.INFO) {
            message = activity()?.getString(msgRes)
        })
    }

    fun showWarningDialog(msg: String?) {
        showInfoDialog(InfoDialog.Options.create(MessageType.WARNING) { message = msg })
    }

    fun showWarningDialog(msgRes: Int) = showWarningDialog(activity()?.getString(msgRes))

    fun showErrorDialog(@StringRes errorRes: Int) = showErrorDialog(activity()?.getString(errorRes))

    fun showErrorDialog(error: String?) {
        showInfoDialog(InfoDialog.Options.create(MessageType.EXCEPTION) { message = error })
    }

    fun showInfoDialog(options: InfoDialog.Options = InfoDialog.Options.defaultOptions()) {
        if (options.message == null) return
        activity()?.run {
            InfoDialog.options = options
            InfoDialog.show(this)
        }
    }
}

interface InfoSheetAlertable {
    fun activity(): FragmentActivity?

    fun showMessageSheet(msg: String?) {
        showInfoSheet(InfoSheet.Options.create(MessageType.INFO) { message = msg })
    }

    fun showMessageSheet(@StringRes msgRes: Int) {
        showInfoSheet(InfoSheet.Options.create(MessageType.INFO) {
            message = activity()?.getString(msgRes)
        })
    }

    fun showWarningSheet(msg: String?) {
        showInfoSheet(InfoSheet.Options.create(MessageType.WARNING) { message = msg })
    }

    fun showWarningSheet(@StringRes msgRes: Int) = showWarningSheet(activity()?.getString(msgRes))

    fun showErrorSheet(@StringRes errorRes: Int) = showErrorSheet(activity()?.getString(errorRes))

    fun showErrorSheet(error: String?) {
        showInfoSheet(InfoSheet.Options.create(MessageType.EXCEPTION) { message = error })
    }

    private fun showInfoSheet(options: InfoSheet.Options = InfoSheet.Options.defaultOptions()) {
        if (options.message == null) return
        activity()?.run {
            InfoSheet.options = options
            InfoSheet.show(this)
        }
    }

}

interface RetryDialogAlertable {
    fun activity(): FragmentActivity?

    fun showRetryDialog(
            message: String,
            options: RetryDialog.Options = RetryDialog.Options.defaultOptions()) {
        activity()?.run {
            options.message = message
            RetryDialog.options = options
            RetryDialog.show(this)
        }
    }

    fun showRetryDialog(
            @StringRes messageRes: Int,
            options: RetryDialog.Options = RetryDialog.Options.defaultOptions()) {
        activity()?.run { showRetryDialog(getString(messageRes), options) }
    }
}

interface RetrySheetAlertable {
    fun activity(): FragmentActivity?

    fun showRetrySheet(
            message: String,
            options: RetrySheet.Options = RetrySheet.Options.defaultOptions()) {
        activity()?.run {
            options.message = message
            RetrySheet.options = options
            RetrySheet.show(this)
        }
    }

    fun showRetrySheet(
            @StringRes messageRes: Int,
            options: RetrySheet.Options = RetrySheet.Options.defaultOptions()) {
        activity()?.run { showRetrySheet(getString(messageRes), options) }
    }
}

interface ToastAlertable {
    fun activity(): FragmentActivity?

    fun longToast(@StringRes message: Int) {
        Toast.makeText(activity(), message, Toast.LENGTH_LONG).show()
    }

    fun longToast(message: String) {
        Toast.makeText(activity(), message, Toast.LENGTH_LONG).show()
    }

    fun shortToast(message: String) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show()
    }

    fun shortToast(@StringRes message: Int) {
        Toast.makeText(activity(), message, Toast.LENGTH_SHORT).show()
    }
}

interface FlashBarAlertable {
    fun activity(): FragmentActivity?

    fun showMessageInFlashBar(builder: Flashbar.Builder) {
        activity()?.run { builder.build().show() }
    }

    fun showErrorInFlashBar(message: String, duration: Long = 6000) {
        activity()?.run {
            showMessageInFlashBar(
                    Flashbar.Builder(this)
                            .message(message)
                            .gravity(Flashbar.Gravity.TOP)
                            .duration(duration)
                            .dismissOnTapOutside()
                            .enableSwipeToDismiss()
                            .backgroundColorRes(R.color.orange_light))
        }
    }

    fun showErrorInFlashBar(@StringRes messageRes: Int, duration: Long = 6000) {
        activity()?.run { showErrorInFlashBar(getString(messageRes), duration) }
    }
}