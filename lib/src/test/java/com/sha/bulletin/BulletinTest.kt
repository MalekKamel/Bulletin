package com.sha.bulletin

import org.junit.Before
import org.junit.Test

class BulletinTest {
    lateinit var bulletin: FakeBulletin

    @Before
    fun setup() {
        bulletin = FakeBulletin()
        BulletinManager.displayedBulletins = mutableSetOf(bulletin)
    }

    @Test
    fun isBulletinDisplayedWithInstance_returnsTrue() {
        assert(isBulletinDisplayed(bulletin))
    }

    @Test
    fun isBulletinDisplayedWithName_returnsTrue() {
        assert(isAnyBulletinDisplayed(bulletin.name))
    }

    @Test
    fun isBulletinDisplayedWithContent_returnsTrue() {
        assert(isAnyBulletinDisplayed(bulletin.name))
    }

    @Test
    fun isBulletinDisplayedWithNameAndContent_returnsTrue() {
        assert(isAnyBulletinDisplayed(bulletin.name, bulletin.content))
    }

    @Test
    fun dismissAllBulletins_dismissShouldBeCalled() {
        dismissAllBulletins()
        assert(bulletin.isDismissed)
    }

    @Test
    fun dismissBulletinWithName_dismissShouldBeCalled() {
        dismissBulletinWithName(bulletin.name)
        assert(bulletin.isDismissed)
    }

    @Test
    fun dismissBulletinWithContent_dismissShouldBeCalled() {
        dismissBulletinWithContent(bulletin.content)
        assert(bulletin.isDismissed)
    }

    @Test
    fun dismissBulletinWithNameAndContent_dismissShouldBeCalled() {
        dismissBulletin(bulletin.name, bulletin.content)
        assert(bulletin.isDismissed)
    }
}