package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import com.nhaarman.mockitokotlin2.mock
import org.junit.Before
import org.junit.Test

class BulletinManagerTest {
    private lateinit var activity: FragmentActivity

    @Before
    fun setup() {
        activity = mock()
        BulletinConfig.queueStrategies.clear()
        BulletinManager.displayedBulletins.clear()
        QueueManager.queue.clear()
    }

    @Test
    fun `show should invoke showBulletin in happy scenario`() {
        val bulletin = FakeBulletin()
        BulletinManager.show(bulletin, activity)
        assert(bulletin.isShowInvoked)
    }

    @Test
    fun `show should add to displayed in happy scenario`() {
        val bulletin = FakeBulletin()
        BulletinManager.show(bulletin, activity)
        assert(BulletinManager.displayedBulletins.first() is FakeBulletin)
    }

    @Test
    fun `show should set status DISPLAYED in happy scenario`() {
        val bulletin = FakeBulletin()
        BulletinManager.show(bulletin, activity)
        assert(bulletin.status == BulletinStatus.DISPLAYED)
    }

    @Test
    fun `show shouldn't invoke showBulletin if it's duplicate`() {
        val bulletin = FakeBulletin()
        bulletin.duplicateStrategy = NameDuplicateStrategy()
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(!bulletin.isShowInvoked)
    }

    @Test
    fun `show should set status IGNORED if it's duplicate`() {
        val bulletin = FakeBulletin()
        bulletin.duplicateStrategy = NameDuplicateStrategy()
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(bulletin.status == BulletinStatus.IGNORED)
    }

    @Test
    fun `show shouldn't change size of displayed bulletins if it's duplicate`() {
        val bulletin = FakeBulletin()
        bulletin.duplicateStrategy = NameDuplicateStrategy()
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(BulletinManager.displayedBulletins.size == 1)
    }

    @Test
    fun `show shouldn't invoke showBulletin if it should be queued`() {
        val bulletin = FakeBulletin()
        BulletinConfig.queueStrategies { + AllQueueStrategy() }
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(!bulletin.isShowInvoked)
    }

    @Test
    fun `show should set status QUEUED if it should be queued`() {
        val bulletin = FakeBulletin()
        BulletinConfig.queueStrategies { + AllQueueStrategy() }
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
        assert(bulletin.status == BulletinStatus.QUEUED)
    }

    @Test
    fun `show shouldn't add to displayed if it should be queued`() {
        BulletinManager.displayedBulletins.clear()
        val bulletin = FakeBulletin()
        BulletinConfig.queueStrategies { + AllQueueStrategy() }
        BulletinManager.displayedBulletins.add(FakeBulletin())

        BulletinManager.show(bulletin, activity)
    }

    @Test
    fun `show shouldn't add to displayed in case status DISPLAYED`() {
        val bulletin = FakeBulletin()
        bulletin.status = BulletinStatus.DISPLAYED
        BulletinManager.show(bulletin, activity)

        assert(BulletinManager.displayedBulletins.isEmpty())
    }

    @Test
    fun `show shouldn't invoke showBulletin in case status DISPLAYED`() {
        val bulletin = FakeBulletin()
        bulletin.status = BulletinStatus.DISPLAYED
        BulletinManager.show(bulletin, activity)

        assert(!bulletin.isShowInvoked)
    }

    @Test
    fun `onDismiss should show next bulletin in queue`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        val next = FakeBulletin()
        QueueManager.queue.add(next)

        BulletinManager.onDismiss(bulletin, activity)
        assert(next.isShowInvoked)
    }


    @Test
    fun `onDismiss should return if status DISMISSED`() {
        val bulletin = FakeBulletin()
        bulletin.status = BulletinStatus.DISMISSED

        BulletinManager.displayedBulletins.add(bulletin)

        BulletinManager.onDismiss(bulletin, activity)

        assert(BulletinManager.displayedBulletins.size == 1)
    }

    @Test
    fun `isAnyDisplayed should return true`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        BulletinManager.isAnyDisplayed()
        assert(BulletinManager.isAnyDisplayed())
    }

    @Test
    fun `isAnyDisplayed should return false`() {
        assert(!BulletinManager.isAnyDisplayed())
    }

    @Test
    fun `isDisplayed with instance should return true`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        assert(BulletinManager.isDisplayed(bulletin))
    }

    @Test
    fun `isDisplayed with instance should return false`() {
        assert(!BulletinManager.isAnyDisplayed())
    }

    @Test
    fun `isDisplayed with name should return true`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        assert(BulletinManager.isAnyDisplayed(bulletin.name))
    }

    @Test
    fun `isDisplayed with name should return false`() {
        assert(!BulletinManager.isAnyDisplayed(FakeBulletin().name))
    }

    @Test
    fun `isAnyDisplayedWithContent should return true`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        assert(BulletinManager.isAnyDisplayedWithContent(bulletin.content))
    }

    @Test
    fun `isAnyDisplayedWithContent should return false`() {
        assert(!BulletinManager.isAnyDisplayedWithContent(FakeBulletin().content))
    }

    @Test
    fun `isAnyDisplayed name and content should return true`() {
        val bulletin = FakeBulletin()
        BulletinManager.displayedBulletins.add(bulletin)
        assert(BulletinManager.isAnyDisplayed(bulletin.name, bulletin.content))
    }

    @Test
    fun `isAnyDisplayed with name and content should return false`() {
        val bulletin = FakeBulletin()
        assert(!BulletinManager.isAnyDisplayed(bulletin.name, bulletin.content))
    }

    @Test
    fun `dismissAll should work correctly`() {
        val bulletins = setOf(FakeBulletin(), FakeBulletin(), FakeBulletin(), FakeBulletin())
        BulletinManager.displayedBulletins.addAll(bulletins)

        BulletinManager.dismissAll()

        BulletinManager.displayedBulletins.forEach { assert((it as FakeBulletin).isDismissed) }
    }

}