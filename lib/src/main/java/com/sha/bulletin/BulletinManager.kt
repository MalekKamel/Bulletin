package com.sha.bulletin

import androidx.fragment.app.FragmentActivity

/**
 * This class is responsible for managing [Bulletin]s like storing bulletin instances,
 * adding & removing bulletins, and checking if a bulletin is displayed.
 */
object BulletinManager {
    /**
     * [Bulletin] instances, this set contains the displayed [Bulletin]s only.
     * The instance is added when the [Bulletin] is displayed and removed when it's destroyed.
     */
    internal var displayedBulletins: MutableSet<Bulletin> = mutableSetOf()

    /**
     * This function is called on dismissing a [Bulletin]. If you implement your custom bulletin,
     * don't forget to call this function to avoid memory leaks.
     */
    @JvmStatic
    fun onDismiss(bulletin: Bulletin, activity: FragmentActivity?) {
        if (bulletin.status == BulletinStatus.DISMISSED) return
        bulletin.status = BulletinStatus.DISMISSED
        displayedBulletins.remove(bulletin)
        QueueManager.showNext(activity)
    }

    @JvmStatic
    fun show(bulletin: Bulletin, activity: FragmentActivity?) {
        activity?.run {
            if (bulletin.status == BulletinStatus.DISPLAYED) return
            if (bulletin.duplicateStrategy.shouldIgnore(bulletin, displayedBulletins)) {
                bulletin.status = BulletinStatus.IGNORED
                return
            }
            if(QueueManager.canQueue(bulletin)) {
                bulletin.status = BulletinStatus.QUEUED
                return
            }
            bulletin.showBulletin(activity)
            bulletin.status = BulletinStatus.DISPLAYED
            displayedBulletins.add(bulletin)
        }
    }

    /**
     * Returns true if the [Bulletin] is displayed
     */
    @JvmStatic
    fun isDisplayed(bulletin: Bulletin) = displayedBulletins.any { it == bulletin }

    /**
     * Returns true if any [Bulletin] is displayed
     */
    @JvmStatic
    fun isAnyDisplayed() = displayedBulletins.isNotEmpty()

    /**
     * Returns true if the [Bulletin] is displayed
     * @param name of the bulletin
     */
    @JvmStatic
    fun isAnyDisplayed(name: String): Boolean = displayedBulletins.any { it.name == name }

    /**
     * Returns true if the [Bulletin] is displayed
     * @param content of the bulletin
     */
    @JvmStatic
    fun isAnyDisplayedWithContent(content: String) = displayedBulletins.any { it.content == content }

    /**
     * Returns true if the [Bulletin] is displayed
     * @param name of the bulletin
     * @param content of the bulletin
     */
    @JvmStatic
    fun isAnyDisplayed(name: String, content: String): Boolean {
        return displayedBulletins.filter { it.name == name }.any { it.content == content }
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
        displayedBulletins.toMutableList().forEach { it.dismiss() }
    }

    /**
     * Dismiss [Bulletin] with its name
     * @param name of the bulletin
     */
    @JvmStatic
    fun dismissWithName(name: String) {
        // Look at dismissAll()
        displayedBulletins.toMutableList()
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
        displayedBulletins.toMutableList()
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
        displayedBulletins.toMutableList()
                .filter { it.name == name && it.content == content }
                .forEach { it.dismiss() }
    }
}

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isBulletinDisplayed(bulletin: Bulletin) = BulletinManager.isDisplayed(bulletin)

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isAnyBulletinDisplayed() = BulletinManager.isAnyDisplayed()

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isAnyBulletinDisplayed(name: String) = BulletinManager.isAnyDisplayed(name)

/**
 * @see [BulletinManager.isAnyDisplayedWithContent]
 */
fun isAnyDisplayedWithContent(content: String) = BulletinManager.isAnyDisplayedWithContent(content)

/**
 * @see [BulletinManager.isDisplayed]
 */
fun isAnyBulletinDisplayed(name: String, content: String) = BulletinManager.isAnyDisplayed(name, content)

/**
 * @see [BulletinManager.dismissAll]
 */
fun dismissAllBulletins() = BulletinManager.dismissAll()

/**
 * @see [BulletinManager.dismissWithName]
 */
fun dismissBulletinWithName(name: String) = BulletinManager.dismissWithName(name)

/**
 * @see [BulletinManager.dismissWithContent]
 */
fun dismissBulletinWithContent(content: String) = BulletinManager.dismissWithContent(content)

/**
 * @see [BulletinManager.dismiss]
 */
fun dismissBulletin(name: String, content: String) = BulletinManager.dismiss(name, content)

/**
 * @see [BulletinManager.show]
 */
fun showBulletin(bulletin: Bulletin, activity: FragmentActivity?) = BulletinManager.show(bulletin, activity)
