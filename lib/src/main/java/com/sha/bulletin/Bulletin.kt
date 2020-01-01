package com.sha.bulletin

import androidx.fragment.app.FragmentActivity

interface Bulletin {
    val name: String
    val content: String
    var status: BulletinStatus
    var duplicateStrategy: DuplicateStrategy
    fun showBulletin(activity: FragmentActivity?)
    fun dismiss()
}

enum class BulletinStatus {
    PENDING,
    QUEUED,
    DISPLAYED,
    DISMISSED,
    IGNORED
}