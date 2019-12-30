package com.sha.bulletin

interface Bulletin {
    val name: String
    val content: String
    fun dismiss()
}