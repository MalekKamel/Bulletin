package com.sha.bulletin

import androidx.fragment.app.FragmentActivity

interface Bulletin {
    val name: String
    val content: String
    fun showBulletin(activity: FragmentActivity?)
    fun dismiss()
}