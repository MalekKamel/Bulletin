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

    @Test
    fun testDefaultStrategy() {
        val strategy = DefaultDuplicateStrategy()
        assert(!strategy.shouldIgnore(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun testNameStrategy_returnsFalse() {
        val strategy = NameDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin("x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun testNameStrategy_returnsTrue() {
        val strategy = NameDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin("FakeBulletin"), BulletinManager.displayedBulletins)
        assert(ignore)
    }

    @Test
    fun testContentStrategy_returnsFalse() {
        val strategy = ContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun testContentStrategy_returnsTrue() {
        val strategy = ContentDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.displayedBulletins)
        assert(ignore)
    }

    @Test
    fun testNameContentStrategy_returnsFalse() {
        val strategy = NameContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun testNameContentStrategy_returnsTrue() {
        val strategy = ContentDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.displayedBulletins)
        assert(ignore)
    }

    @Test
    fun testSingleStrategy_returnsFalse() {
        val strategy = SingleDuplicateStrategy()
        BulletinManager.displayedBulletins = mutableSetOf()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.displayedBulletins))
    }

    @Test
    fun testSingleStrategy_returnsTrue() {
        val strategy = SingleDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.displayedBulletins)
        assert(ignore)
    }
}