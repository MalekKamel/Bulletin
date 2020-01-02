package com.sha.bulletin

import org.junit.Before
import org.junit.Test

class DuplicateStrategyTest {
    lateinit var bulletin: FakeBulletin

    @Before
    fun setup() {
        bulletin = FakeBulletin()
        BulletinManager.displayedBulletins = mutableSetOf(bulletin)
    }

    // DefaultDuplicateStrategy

    @Test
    fun `DefaultDuplicateStrategy should return true`() {
        val strategy = DefaultDuplicateStrategy()
        assert(!strategy.shouldIgnore(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun `DefaultDuplicateStrategy IgnoreDuplicateStrategy should be DROP`() {
        val strategy = DefaultDuplicateStrategy()
        assert(strategy.onIgnoreStrategy == IgnoreDuplicateStrategy.DROP)
    }

    // NameDuplicateStrategy

    @Test
    fun `NameDuplicateStrategy should return true`() {
        val strategy = NameDuplicateStrategy()
        assert(strategy.shouldIgnore(FakeBulletin("FakeBulletin"), BulletinManager.displayedBulletins))
    }

    @Test
    fun `NameDuplicateStrategy should return false`() {
        val strategy = NameDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin("x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun `NameDuplicateStrategy IgnoreDuplicateStrategy should be QUEUE`() {
        val strategy = NameDuplicateStrategy()
        assert(strategy.onIgnoreStrategy == IgnoreDuplicateStrategy.QUEUE)
    }

    // ContentDuplicateStrategy

    @Test
    fun `ContentDuplicateStrategy should return false`() {
        val strategy = ContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun `ContentDuplicateStrategy should return true`() {
        val strategy = ContentDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.displayedBulletins)
        assert(ignore)
    }

    @Test
    fun `ContentDuplicateStrategy IgnoreDuplicateStrategy should be DROP`() {
        val strategy = ContentDuplicateStrategy()
        assert(strategy.onIgnoreStrategy == IgnoreDuplicateStrategy.DROP)
    }

    // NameContentDuplicateStrategy

    @Test
    fun `NameContentDuplicateStrategy should return false`() {
        val strategy = NameContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun `NameContentDuplicateStrategy IgnoreDuplicateStrategy should be DROP`() {
        val strategy = NameContentDuplicateStrategy()
        assert(strategy.onIgnoreStrategy == IgnoreDuplicateStrategy.DROP)
    }

    // SingleDuplicateStrategy

    @Test
    fun `SingleDuplicateStrategy should return false`() {
        val strategy = SingleDuplicateStrategy()
        BulletinManager.displayedBulletins = mutableSetOf()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun `SingleDuplicateStrategy should return true`() {
        val strategy = SingleDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.displayedBulletins)
        assert(ignore)
    }

    @Test
    fun `SingleDuplicateStrategy IgnoreDuplicateStrategy should be QUEUE`() {
        val strategy = SingleDuplicateStrategy()
        assert(strategy.onIgnoreStrategy == IgnoreDuplicateStrategy.QUEUE)
    }
}