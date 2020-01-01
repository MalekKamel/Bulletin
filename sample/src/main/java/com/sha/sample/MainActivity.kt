package com.sha.sample

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.sha.bulletin.*
import com.sha.bulletin.sheet.InfoSheet
import com.sha.formvalidatorsample.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), MyAlertable {

    private val message = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        BulletinConfig.queueStrategies {
            + SheetQueueStrategy()
            + ToastQueueStrategy()
        }

        sheet()
        dialog()
        flashBar()
        toast()
        manageBulletins()
        snackbar()
    }

    private fun snackbar() {
        btnSnackbar.setOnClickListener { showSnackabr(coordinator, message, Snackbar.LENGTH_LONG) }
    }

    private fun sheet() {
        btnMessageSheet.setOnClickListener {
            showMessageSheet("Sheet1, after dismissing, Sheet2 will be displayed!")
            showMessageSheet("Sheet2, after dismissing, Sheet3 will be displayed!")
            showMessageSheet("Sheet3")
        }

        btnWarningSheet.setOnClickListener {
            showInfoSheet(InfoSheet.Options.create {
                title = "InfoSheet"
                content = message
                iconSetup = IconSetup.create {
                    iconDrawableRes = R.drawable.ic_error
                    containerColorRes = R.color.colorPrimary
                }
            })
        }

        btnErrorSheet.setOnClickListener { showErrorSheet(message) }
        btnRetrySheet.setOnClickListener { showRetrySheet(message) }
    }

    private fun dialog() {
        btnMessageDialog.setOnClickListener { showMessageDialog(message)  }
        btnWarningDialog.setOnClickListener { showWarningDialog(message) }
        btnErrorDialog.setOnClickListener { showErrorDialog(message) }
        btnRetryDialog.setOnClickListener { showRetryDialog(message) }
        btnCustomLoadingDialog.setOnClickListener { showCustomLoadingDialog(message) }
    }

    private fun flashBar() {
        btnFlashBar.setOnClickListener { showErrorInFlashBar(message) }
    }

    private fun toast() {
        btnShortToast.setOnClickListener {
            shortToast("Toast1! after dismissing, Toast2 will be displayed!")
            shortToast("Toast2! after dismissing, Toast3 will be displayed!")
            shortToast("Toast3")
        }
        btnLongToast.setOnClickListener { longToast(message) }
    }

    private fun manageBulletins() {
        btnDismissAllBulletins.setOnClickListener {
            showMessageSheet("Will be dismissed after 2 seconds!")
            showRetryDialog("Will be dismissed after 2 seconds!")
            longToast("Will be dismissed after 2 seconds!")
            showErrorInFlashBar("Will be dismissed after 2 seconds!")
            showSnackabr(coordinator, "Will be dismissed after 2 seconds!", Snackbar.LENGTH_LONG)
            // Dismiss all bulletins after 2 seconds
            Handler().postDelayed({ dismissAllBulletins() }, TimeUnit.SECONDS.toMillis(2))
        }
    }

    override fun activity(): FragmentActivity?  = this
}
