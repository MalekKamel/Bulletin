package com.sha.bulletin

class FakeBulletin(
        override val name: String = "FakeBulletin",
        override val content: String = "content"
): Bulletin {
    var isDismissed = false
    override fun dismiss() { isDismissed = true }
}