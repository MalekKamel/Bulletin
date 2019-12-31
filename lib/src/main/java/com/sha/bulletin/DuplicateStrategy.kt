package com.sha.bulletin

interface DuplicateStrategy {
    fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}

class DefaultDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = false
}

class NameDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyDisplayed(bulletin.name)
    }
}

class ContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyDisplayedWithContent(bulletin.content)
    }
}

class NameContentDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return isAnyDisplayed(bulletin.name, bulletin.content)
    }
}

class SingleDuplicateStrategy: DuplicateStrategy {
    override fun shouldIgnore(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = isAnyDisplayed()
}