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
        @JvmStatic
        var bulletins: MutableSet<Bulletin> = mutableSetOf()

        /**
         * The [Bulletin] is added only if it's visible or will be visible to the user
         */
        @JvmStatic
        fun add(bulletin: Bulletin) = bulletins.add(bulletin)

        /**
         * The [Bulletin] is removed only if it's destroyed or will be destroyed.
         */
        @JvmStatic
        fun remove(bulletin: Bulletin) = bulletins.remove(bulletin)

        /**
         * Returns true if the [Bulletin] is displayed
         */
        @JvmStatic
        fun isAnyDisplayed(bulletin: Bulletin) = bulletins.any { it == bulletin }

        /**
         * Returns true if any [Bulletin] is displayed
         */
        @JvmStatic
        fun isAnyDisplayed() = bulletins.isNotEmpty()

        /**
         * Returns true if the [Bulletin] is displayed
         * @param name of the bulletin
         */
        @JvmStatic
        fun isAnyDisplayed(name: String): Boolean {
          return bulletins.any {
                it.name == name
            }
        }

        /**
         * Returns true if the [Bulletin] is displayed
         * @param content of the bulletin
         */
        @JvmStatic
        fun isAnyDisplayedWithContent(content: String) = bulletins.any { it.content == content }

        /**
         * Returns true if the [Bulletin] is displayed
         * @param name of the bulletin
         * @param content of the bulletin
         */
        @JvmStatic
        fun isAnyDisplayed(name: String, content: String): Boolean {
            return bulletins.filter { it.name == name }.any { it.content == content }
        }

        /**
         * Dismiss all [Bulletin]s
         */
        @JvmStatic
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
        @JvmStatic
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
        @JvmStatic
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
        @JvmStatic
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
 * @see [BulletinManager.isAnyDisplayed]
 */
fun isAnyDisplayed(bulletin: Bulletin) = BulletinManager.isAnyDisplayed(bulletin)

/**
 * @see [BulletinManager.isAnyDisplayed]
 */
fun isAnyDisplayed() = BulletinManager.isAnyDisplayed()

/**
 * @see [BulletinManager.isAnyDisplayed]
 */
fun isAnyDisplayed(name: String) = BulletinManager.isAnyDisplayed(name)

/**
 * @see [BulletinManager.isAnyDisplayedWithContent]
 */
fun isAnyDisplayedWithContent(content: String) = BulletinManager.isAnyDisplayedWithContent(content)

/**
 * @see [BulletinManager.isAnyDisplayed]
 */
fun isAnyDisplayed(name: String, content: String) = BulletinManager.isAnyDisplayed(name, content)

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
