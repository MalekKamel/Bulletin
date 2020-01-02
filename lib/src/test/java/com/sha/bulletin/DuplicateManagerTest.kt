package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

class DuplicateManagerTest {

    @Before
    fun setup() {
        BulletinConfig.queueStrategies.clear()
        BulletinManager.displayedBulletins.clear()
        QueueManager.clear()
    }

    @Test
    fun `tryIgnore should return true if it's duplicate`() {
        val bulletin = FakeBulletin()

        val duplicateStrategy = NameDuplicateStrategy()
        bulletin.duplicateStrategy = duplicateStrategy

        BulletinManager.displayedBulletins.add(FakeBulletin())

        assert(DuplicateManager.tryIgnore(bulletin))
    }

    @Test
    fun `tryIgnore should return true if it's NOT duplicate`() {
        val bulletin = FakeBulletin()

        val duplicateStrategy = NameDuplicateStrategy()
        bulletin.duplicateStrategy = duplicateStrategy

        assert(!DuplicateManager.tryIgnore(bulletin))
    }

    @Test
    fun `tryIgnore should set status IGNORED if it's duplicate and IgnoreDuplicateStrategy DROP`() {
        val bulletin = FakeBulletin()

        val duplicateStrategy = NameDuplicateStrategy()
        duplicateStrategy.onIgnoreStrategy = IgnoreDuplicateStrategy.DROP
        bulletin.duplicateStrategy = duplicateStrategy

        BulletinManager.displayedBulletins.add(FakeBulletin())

        DuplicateManager.tryIgnore(bulletin)
        assert(bulletin.status == BulletinStatus.IGNORED)
    }


    @Test
    fun `show should set status QUEUED if it's duplicate and IgnoreDuplicateStrategy QUEUE`() {
        val bulletin = FakeBulletin()
        val duplicateStrategy = NameDuplicateStrategy()
        bulletin.duplicateStrategy = duplicateStrategy
        BulletinManager.displayedBulletins.add(FakeBulletin())
        BulletinConfig.queueStrategies { + AllQueueStrategy() }

        DuplicateManager.tryIgnore(bulletin)
        assert(bulletin.status == BulletinStatus.QUEUED)
    }
}