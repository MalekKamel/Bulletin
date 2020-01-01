package com.sha.bulletin

/**
 * A strategy used to manage duplicate bulletins behavior.
 */
interface DuplicateStrategy {
    fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}

/**
 * This strategy allows all bulletins to be duplicated.
 */
class DefaultDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = false
}

/**
 * This strategy prevents duplicating the [Bulletin] if there's another [Bulletin]
 * with the same name displayed.
 */
class NameDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyBulletinDisplayed(bulletin.name)
    }
}

/**
 * This strategy prevents duplicating the [Bulletin] if there's another [Bulletin]
 * with the same content displayed.
 */
class ContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyDisplayedWithContent(bulletin.content)
    }
}

/**
 * This strategy prevents duplicating the [Bulletin] if there's another [Bulletin]
 * with the same name & content displayed.
 */
class NameContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyBulletinDisplayed(bulletin.name, bulletin.content)
    }
}

/**
 * This strategy allows displaying the [Bulletin] only if there're no any bulletins
 * displayed. In another words, it allows a single [Bulletin] to be displayed at a time
 * and ignores any other bulletins.
 */
class SingleDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = isAnyBulletinDisplayed()
}