package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

class BulletinManagerTest {
    lateinit var bulletin: FakeBulletin
    lateinit var activity: FragmentActivity

    @Before
    fun setup() {
        bulletin = FakeBulletin()
        activity = mock()
        BulletinConfig.queueStrategies.clear()
    }

    @Test
    fun `show should invoke showBulletin and add to displayed and set status DISPLAYED`() {
        BulletinManager.show(bulletin, activity)
        assert(bulletin.isShowInvoked)
        assert(BulletinManager.displayedBulletins.first() is FakeBulletin)
        assert(bulletin.status == BulletinStatus.DISPLAYED)
    }

    @Test
    fun `show shouldn't invoke showBulletin and should set status IGNORED`() {
        bulletin.duplicateStrategy = NameDuplicateStrategy()
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(!bulletin.isShowInvoked)
        assert(bulletin.status == BulletinStatus.IGNORED)
        assert(BulletinManager.displayedBulletins.size == 1)
    }

    @Test
    fun `show shouldn't invoke showBulletin and should set status QUEUED`() {
        BulletinConfig.queueStrategies { + AllQueueStrategy() }
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(!bulletin.isShowInvoked)
        assert(bulletin.status == BulletinStatus.QUEUED)
        assert(BulletinManager.displayedBulletins.size == 1)
    }

    @Test
    fun `show shouldn't invoke showBulletin in case status DISPLAYED`() {
        bulletin.status = BulletinStatus.DISPLAYED
        BulletinManager.show(bulletin, activity)

        assert(!bulletin.isShowInvoked)
        assert(bulletin.status == BulletinStatus.DISPLAYED)
        assert(BulletinManager.displayedBulletins.size == 0)
    }

    @Test
    fun `onDismiss should set status DISMISSED, remove from displayed and show next`() {
        BulletinManager.displayedBulletins.add(bulletin)
        val next = FakeBulletin()
        QueueManager.queue.add(next)

        BulletinManager.onDismiss(bulletin, activity)

        assert(bulletin.status == BulletinStatus.DISMISSED)
        assert(next.isShowInvoked)
        assert(BulletinManager.displayedBulletins.size == 0)
    }
}