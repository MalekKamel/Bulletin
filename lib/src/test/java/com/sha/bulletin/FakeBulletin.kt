package com.sha.bulletin

import androidx.fragment.app.FragmentActivity

class FakeBulletin(
        override val name: String = "FakeBulletin",
        override val content: String = "content"
): Bulletin {
    var isDismissed = false
    override fun dismiss() { isDismissed = true }
    override fun showBulletin(activity: FragmentActivity?) {}
}