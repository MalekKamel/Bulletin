package com.sha.bulletin

var bulletins: MutableList<Bulletin> = mutableListOf()

interface Bulletin {
    val name: String
    val content: String
    fun dismiss()
}

fun isBulletinDisplayed(bulletin: Bulletin) = bulletins.any { it == bulletin }

fun isBulletinDisplayed(name: String) = bulletins.any { it.name == name }

fun isBulletinWithContentDisplayed(content: String, name: String): Boolean {
   return bulletins.filter { it.name == name }.any { it.content == content }
}

fun dismissAllBulletins() { bulletins.forEach { it.dismiss() } }

fun dismissBulletinWithName(name: String) {
    bulletins.firstOrNull { it.name == name }?.run { bulletins.remove(this) }
}

fun dismissBulletinWithContent(content: String) {
    bulletins.firstOrNull { it.content == content }?.run { bulletins.remove(this) }
}

object BulletinConfig {
    var defaultColor = R.color.blue
    var isCancellable: Boolean = true
    var ignoreIfSameContentDisplayed: Boolean = true
    var iconSetup: IconSetup = IconSetup.default()
}