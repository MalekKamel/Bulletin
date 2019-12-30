package com.sha.bulletin

import org.junit.Before
import org.junit.Test

class DuplicateStrategyTest {
    lateinit var bulletin: FakeBulletin

    @Before
    fun setup() {
        bulletin = FakeBulletin()
        BulletinManager.bulletins = mutableSetOf(bulletin)
    }

    @Test
    fun testDefaultStrategy() {
        val strategy = DefaultDuplicateStrategy()
        assert(!strategy.shouldIgnore(bulletin, BulletinManager.bulletins))
    }

    @Test
    fun testNameStrategy_returnsFalse() {
        val strategy = NameDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin("x"), BulletinManager.bulletins))
    }

    @Test
    fun testNameStrategy_returnsTrue() {
        val strategy = NameDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin("FakeBulletin"), BulletinManager.bulletins)
        assert(ignore)
    }

    @Test
    fun testContentStrategy_returnsFalse() {
        val strategy = ContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(content = "x"), BulletinManager.bulletins))
    }

    @Test
    fun testContentStrategy_returnsTrue() {
        val strategy = ContentDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.bulletins)
        assert(ignore)
    }

    @Test
    fun testNameContentStrategy_returnsFalse() {
        val strategy = NameContentDuplicateStrategy()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.bulletins))
    }

    @Test
    fun testNameContentStrategy_returnsTrue() {
        val strategy = ContentDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.bulletins)
        assert(ignore)
    }

    @Test
    fun testSingleStrategy_returnsFalse() {
        val strategy = SingleDuplicateStrategy()
        BulletinManager.bulletins = mutableSetOf()
        assert(!strategy.shouldIgnore(FakeBulletin(name = "FakeBulletin", content = "x"), BulletinManager.bulletins))
    }

    @Test
    fun testSingleStrategy_returnsTrue() {
        val strategy = SingleDuplicateStrategy()
        val ignore = strategy.shouldIgnore(FakeBulletin(content = "content"), BulletinManager.bulletins)
        assert(ignore)
    }
}