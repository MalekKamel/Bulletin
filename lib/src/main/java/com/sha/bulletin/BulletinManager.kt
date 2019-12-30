package com.sha.bulletin

/**
 * This class is responsible for managing [Bulletin]s like storing bulletin instances,
 * adding & removing bulletins, and checking if a bulletin is displayed.
 */
class BulletinManager {
    companion object {
        /**
         * [Bulletin] instances, this set contains the displayed [Bulletin]s only.
         * The instance is added when the [Bulletin] is displayed and removed when it's destroyed.
         */
        var bulletins: MutableSet<Bulletin> = mutableSetOf()

        /**
         * The [Bulletin] is added only if it's visible or will be visible to the user
         */
        fun add(bulletin: Bulletin) = bulletins.add(bulletin)

        /**
         * The [Bulletin] is removed only if it's destroyed or will be destroyed.
         */
        fun remove(bulletin: Bulletin) = bulletins.remove(bulletin)

        /**
         * Returns true if the [Bulletin] is displayed
         */
        fun isDisplayed(bulletin: Bulletin) = bulletins.any { it == bulletin }

        /**
         * Returns true if the [Bulletin] is displayed
         * @param name of the bulletin
         */
        fun isDisplayed(name: String) = bulletins.any { it.name == name }

        /**
         * Returns true if the [Bulletin] is displayed
         * @param content of the bulletin
         */
        fun isDisplayedWithContent(content: String) = bulletins.any { it.content == content }

        /**
         * Returns true if the [Bulletin] is displayed
         * @param name of the bulletin
         * @param content of the bulletin
         */
        fun isDisplayed(name: String, content: String): Boolean {
            return bulletins.filter { it.name == name }.any { it.content == content }
        }

        /**
         * Dismiss all [Bulletin]s
         */
        fun dismissAll() {
            // toMutableList() creates a copy of the set, why?
            // to avoid ConcurrentModificationException as Bulletin.dismiss removes the
            // Bulletin from the set while we're looping over it. The solution is to loop
            // over the copied list while we remove the items from the original list.
            bulletins.toMutableList().forEach { it.dismiss() }
        }

        /**
         * Dismiss [Bulletin] with its name
         * @param name of the bulletin
         */
        fun dismissWithName(name: String) {
            // Look at dismissAll()
            bulletins.toMutableList()
                    .filter { it.name == name }
                    .forEach { it.dismiss() }
        }

        /**
         * Dismiss [Bulletin] with its content
         * @param content of the bulletin
         */
        fun dismissWithContent(content: String) {
            // Look at dismissAll()
            bulletins.toMutableList()
                    .filter { it.content == content }
                    .forEach { it.dismiss() }
        }

        /**
         * Dismiss [Bulletin] with its name and content
         * @param name of the [Bulletin]
         * @param content of the [Bulletin]
         */
        fun dismiss(name: String, content: String) {
            // Look at dismissAll()
            bulletins.toMutableList()
                    .filter { it.name == name && it.content == content }
                    .forEach { it.dismiss() }
        }
        
    }
}

/**
 * @see [BulletinManager.bulletins]
 */
var bulletins: MutableSet<Bulletin> = BulletinManager.bulletins

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isDisplayed(bulletin: Bulletin) = BulletinManager.isDisplayed(bulletin)

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isDisplayed(name: String) = BulletinManager.isDisplayed(name)

/**
 * @see [BulletinManager.isDisplayedWithContent]
 */
fun isDisplayedWithContent(content: String) = BulletinManager.isDisplayedWithContent(content)

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isDisplayed(name: String, content: String) = BulletinManager.isDisplayed(name, content)

/**
 * @see [BulletinManager.dismissAll]
 */
fun dismissAll() = BulletinManager.dismissAll()

/**
 * @see [BulletinManager.dismissWithName]
 */
fun dismissWithName(name: String) = BulletinManager.dismissWithName(name)

/**
 * @see [BulletinManager.dismissWithContent]
 */
fun dismissWithContent(content: String) = BulletinManager.dismissWithContent(content)

/**
 * @see [BulletinManager.dismiss]
 */
fun dismiss(name: String, content: String) = BulletinManager.dismiss(name, content)
