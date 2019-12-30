package com.sha.bulletin

interface DuplicateStrategy {
    fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>): Boolean
}

class DefaultDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>) = false
}

class NameDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>): Boolean {
        return isAnyDisplayed(bulletin.name)
    }
}

class ContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>): Boolean {
        return isAnyDisplayedWithContent(bulletin.content)
    }
}

class NameAndContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>): Boolean {
        return isAnyDisplayed(bulletin.name, bulletin.content)
    }
}

class SingleDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: MutableSet<Bulletin>) = isAnyDisplayed()
}