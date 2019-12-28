package com.sha.sample

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Alertable
import com.sha.bulletin.IconSetup
import com.sha.bulletin.dismissAllBulletins
import com.sha.bulletin.sheet.InfoSheet
import com.sha.formvalidatorsample.R
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(), Alertable {

    private val message = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sheet()
        dialog()
        flashBar()
        toast()
        manageBulletins()
    }

    private fun sheet() {
        btnMessageSheet.setOnClickListener {
            showMessageSheet(message)
            showMessageSheet(message)
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
            showWarningSheet(message)
        }

        btnErrorSheet.setOnClickListener { showErrorSheet(message) }
        btnRetrySheet.setOnClickListener { showRetrySheet(message) }
    }

    private fun dialog() {
        btnMessageDialog.setOnClickListener { showMessageDialog(message)  }
        btnWarningDialog.setOnClickListener { showWarningDialog(message) }
        btnErrorDialog.setOnClickListener { showErrorDialog(message) }

        btnRetryDialog.setOnClickListener {
            showRetryDialog(message)
            showRetryDialog(message)
        }
    }

    private fun flashBar() {
        btnFlashBar.setOnClickListener {
            showErrorInFlashBar(message)
            showErrorInFlashBar(message)
        }
    }

    private fun toast() {
        btnShortToast.setOnClickListener { shortToast(message) }
        btnLongToast.setOnClickListener { longToast(message) }
    }

    private fun manageBulletins() {
        btnDismissAllBulletins.setOnClickListener {
            showErrorInFlashBar("Will be dismissed after 2 seconds!")
            showMessageSheet("Will be dismissed after 2 seconds!")
            showRetryDialog("Will be dismissed after 2 seconds!")
            longToast("Will be dismissed after 2 seconds!")
            // Dismiss all bulletins after 2 seconds
            Handler().postDelayed({ dismissAllBulletins() }, TimeUnit.SECONDS.toMillis(2))
        }
    }

    override fun activity(): FragmentActivity?  = this
}
