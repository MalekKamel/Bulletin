package com.sha.bulletin

import androidx.fragment.app.FragmentActivity

class FakeBulletin(
        override val name: String = "FakeBulletin",
        override val content: String = "content"
): Bulletin {
    var isDismissed = false
    var isShowInvoked = false
    override fun dismiss() { isDismissed = true }
    override fun showBulletin(activity: FragmentActivity?) { isShowInvoked = true }
    override var status: BulletinStatus = BulletinStatus.PENDING
    override var duplicateStrategy: DuplicateStrategy = DefaultDuplicateStrategy()
}