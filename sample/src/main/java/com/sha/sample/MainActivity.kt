package com.sha.sample

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Alertable
import com.sha.bulletin.IconSetup
import com.sha.bulletin.bulletins
import com.sha.bulletin.sheet.InfoSheet
import com.sha.formvalidatorsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Alertable {

    private val message = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPrint.setOnClickListener { bulletins.forEach { Log.e("Bulletin Name: ${it?.name}", "Bulletin: $it") } }

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

        btnMessageDialog.setOnClickListener { showMessageDialog(message)  }
        btnWarningDialog.setOnClickListener { showWarningDialog(message) }
        btnErrorDialog.setOnClickListener { showErrorDialog(message) }

        btnRetryDialog.setOnClickListener {
            showRetryDialog(message)
            showRetryDialog(message)
        }

        btnRetrySheet.setOnClickListener { showRetrySheet(message) }

        btnFlashBar.setOnClickListener {
            showErrorInFlashBar(message)
            showErrorInFlashBar(message)
        }

        btnToast.setOnClickListener { longToast(message) }
    }

    override fun activity(): FragmentActivity?  = this
}
