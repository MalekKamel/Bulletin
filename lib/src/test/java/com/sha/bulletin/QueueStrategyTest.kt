package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import com.nhaarman.mockitokotlin2.mock
import com.sha.bulletin.dialog.InfoDialog
import com.sha.bulletin.sheet.InfoSheet
import com.sha.bulletin.toast.StandardToast
import org.junit.Before
import org.junit.Test

class QueueStrategyTest {

    lateinit var bulletin: FakeBulletin

    @Before
    fun setup() {
        bulletin = FakeBulletin()
        BulletinManager.displayedBulletins = mutableSetOf(bulletin)
    }

    // NoneQueueStrategy

    @Test
    fun testNoneQueueStrategy_noQueueWhenIfBulletinDisplayed() {
        BulletinManager.displayedBulletins = mutableSetOf()
        val strategy = NoneQueueStrategy()
        assert(!strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun testNoneQueueStrategy_noQueueWhenIfNoBulletinDisplayed() {
        val strategy = NoneQueueStrategy()
        assert(!strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    // AllQueueStrategy

    @Test
    fun testAllQueueStrategy_queueIfBulletinDisplayed() {
        val strategy = AllQueueStrategy()
        assert(strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun testAllQueueStrategy_queueIfNoBulletinDisplayed() {
        val strategy = AllQueueStrategy()
        assert(strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    // DialogQueueStrategy

    @Test
    fun `DialogQueueStrategy should queue`() {
        val strategy = DialogQueueStrategy()
        assert(strategy.shouldQueue(InfoDialog(), mutableSetOf(InfoDialog())))
    }

    @Test
    fun `DialogQueueStrategy shouldn't queue if bulletin isn't BulletinDialog`() {
        val strategy = DialogQueueStrategy()
        assert(!strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun `DialogQueueStrategy shouldn't queue if no BulletinDialog displayed`() {
        val strategy = DialogQueueStrategy()
        assert(!strategy.shouldQueue(InfoDialog(), mutableSetOf()))
    }

    // SheetQueueStrategy

    @Test
    fun `SheetQueueStrategy should queue`() {
        val strategy = SheetQueueStrategy()
        assert(strategy.shouldQueue(InfoSheet(), mutableSetOf(InfoSheet())))
    }

    @Test
    fun `SheetQueueStrategy shouldn't queue if bulletin isn't BulletinSheet`() {
        val strategy = SheetQueueStrategy()
        assert(!strategy.shouldQueue(bulletin, BulletinManager.displayedBulletins))
    }

    @Test
    fun `SheetQueueStrategy shouldn't queue if no BulletinSheet displayed`() {
        val strategy = SheetQueueStrategy()
        assert(!strategy.shouldQueue(InfoSheet(), mutableSetOf()))
    }

    // ToastQueueStrategy

    @Test
    fun `ToastQueueStrategy should queue`() {
        val activity = mock<FragmentActivity>()
        val strategy = ToastQueueStrategy()
        assert(strategy.shouldQueue(StandardToast(activity), mutableSetOf(StandardToast(activity))))
    }

    @Test
    fun `ToastQueueStrategy shouldn't queue if bulletin isn't BulletinFlashBar`() {
        val activity = mock<FragmentActivity>()
        val strategy = ToastQueueStrategy()
        assert(!strategy.shouldQueue(bulletin, mutableSetOf(StandardToast(activity))))
    }

    @Test
    fun `ToastQueueStrategy shouldn't queue if no BulletinFlashBar displayed`() {
        val activity = mock<FragmentActivity>()
        val strategy = ToastQueueStrategy()
        assert(!strategy.shouldQueue(StandardToast(activity), mutableSetOf()))
    }
}
