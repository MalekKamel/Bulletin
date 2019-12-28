package com.sha.bulletin

var bulletins: MutableSet<Bulletin> = mutableSetOf()

interface Bulletin {
    val name: String
    val content: String
    fun dismiss()
}

/**
 * Returns true if the [Bulletin] is displayed
 */
fun isBulletinDisplayed(bulletin: Bulletin) = bulletins.any { it == bulletin }

/**
 * Returns true if the [Bulletin] is displayed
 * @param name of the bulletin
 */
fun isBulletinDisplayed(name: String) = bulletins.any { it.name == name }

/**
 * Returns true if the [Bulletin] is displayed
 * @param name of the bulletin
 * @param content of the bulletin
 */
fun isBulletinDisplayed(name: String, content: String): Boolean {
   return bulletins.filter { it.name == name }.any { it.content == content }
}

/**
 * Dismiss all [Bulletin]s
 */
fun dismissAllBulletins() {
    // toMutableList() creates a copy of the set, why?
    // to avoid ConcurrentModificationException as Bulletin.dismiss removes the
    // Bulletin from the set while we're looping over it.
    bulletins.toMutableList().forEach { it.dismiss() }
}

/**
 * Dismiss [Bulletin] with its name
 * @param name of the bulletin
 */
fun dismissBulletinWithName(name: String) {
    // Look at dismissAllBulletins()
    bulletins.toMutableList()
            .filter { it.name == name }
            .forEach { it.dismiss() }
}

/**
 * Dismiss [Bulletin] with its content
 * @param content of the bulletin
 */
fun dismissBulletinWithContent(content: String) {
    // Look at dismissAllBulletins()
    bulletins.toMutableList()
            .filter { it.content == content }
            .forEach { it.dismiss() }
}

/**
 * Dismiss [Bulletin] with its name and content
 * @param name of the [Bulletin]
 * @param content of the [Bulletin]
 */
fun dismissBulletin(name: String, content: String) {
    // Look at dismissAllBulletins()
    bulletins.toMutableList()
            .filter { it.name == name && it.content == content }
            .forEach { it.dismiss() }
}

