package com.sha.bulletin

import com.sha.bulletin.dialog.BulletinDialog
import com.sha.bulletin.flashbar.BulletinFlashBar
import com.sha.bulletin.sheet.BulletinSheet
import com.sha.bulletin.toast.BulletinToast

interface QueueStrategy {
    fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}

class DefaultQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return false
    }
}

class DialogQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
       return bulletin is BulletinDialog && displayedBulletins.any { it is BulletinDialog }
    }
}

class SheetQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinSheet && displayedBulletins.any { it is BulletinSheet }
    }
}

class FlashBarQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinFlashBar && displayedBulletins.any { it is BulletinFlashBar }
    }
}

class ToastQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinToast && displayedBulletins.any { it is BulletinToast }
    }
}