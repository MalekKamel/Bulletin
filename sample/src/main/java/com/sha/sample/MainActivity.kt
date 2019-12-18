package com.sha.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.sha.bulletin.Alertable
import com.sha.formvalidatorsample.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), Alertable {

    private val message = """
Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat    """.trimIndent()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnMessageSheet.setOnClickListener { showMessageSheet(message) }
        btnWarningSheet.setOnClickListener { showWarningSheet(message) }
        btnErrorSheet.setOnClickListener { showErrorSheet(message) }

        btnMessageDialog.setOnClickListener { showMessageDialog(message)  }
        btnWarningDialog.setOnClickListener { showWarningDialog(message) }
        btnErrorDialog.setOnClickListener { showErrorDialog(message) }

        btnRetryDialog.setOnClickListener { showRetryDialog(message) }
        btnRetrySheet.setOnClickListener { showRetrySheet(message) }
        btnFlashBar.setOnClickListener { showErrorInFlashBar(message) }

    }

    override fun activity(): FragmentActivity?  = this
}
