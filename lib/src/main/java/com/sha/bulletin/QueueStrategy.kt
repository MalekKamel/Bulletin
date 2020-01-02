package com.sha.bulletin

import com.sha.bulletin.dialog.BulletinDialog
import com.sha.bulletin.flashbar.BulletinFlashbar
import com.sha.bulletin.sheet.BulletinSheet
import com.sha.bulletin.snackbar.BulletinSnackbar
import com.sha.bulletin.toast.BulletinToast

/**
 * A strategy allows adding a queuing behavior for a specific [Bulletin]. The [Bulletin] is qualified
 * for queuing if there's a strategy allows queuing. You can add any number of strategies, and if
 * there's at least one strategy allows queuing the [Bulletin], it will be queued. The queued [Bulletin]
 * will be displayed when the displayed [Bulletin] is dismissed.
 */
interface QueueStrategy {
    fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean
}

/**
 * This strategy disables any [Bulletin] queuing. This is the default behavior of the library.
 */
class NoneQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = false
}

/**
 * This strategy allows queuing any [Bulletin].
 */
class AllQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>) = true
}

/**
 * This strategy allows queuing of [BulletinDialog] only. If there's any number of [BulletinDialog]
 * displayed, the new [Bulletin] will be queued and will be displayed once the displayed one is dismissed.
 */
class DialogQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
       return bulletin is BulletinDialog && displayedBulletins.any { it is BulletinDialog }
    }
}

/**
 * This strategy allows queuing of [BulletinSheet] only. If there's any number of [BulletinSheet]
 * displayed, the new [Bulletin] will be queued and will be displayed once the displayed one is dismissed.
 */
class SheetQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinSheet && displayedBulletins.any { it is BulletinSheet }
    }
}

/**
 * This strategy allows queuing of [BulletinFlashbar] only. If there's any number of [BulletinFlashbar]
 * displayed, the new [Bulletin] will be queued and will be displayed once the displayed one is dismissed.
 */
class FlashBarQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinFlashbar && displayedBulletins.any { it is BulletinFlashbar }
    }
}

/**
 * This strategy allows queuing of [BulletinSnackbar] only. If there's any number of [BulletinSnackbar]
 * displayed, the new [Bulletin] will be queued and will be displayed once the displayed one is dismissed.
 */
class SnackbarQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinSnackbar && displayedBulletins.any { it is BulletinSnackbar }
    }
}

/**
 * This strategy allows queuing of [BulletinToast] only. If there's any number of [BulletinToast]
 * displayed, the new [Bulletin] will be queued and will be displayed once the displayed one is dismissed.
 */
class ToastQueueStrategy: QueueStrategy {
    override fun shouldQueue(bulletin: Bulletin, displayedBulletins: Set<Bulletin>): Boolean {
        return bulletin is BulletinToast && displayedBulletins.any { it is BulletinToast }
    }
}


/**
 * Holds all [QueueStrategy]s and provides operators for adding strategies.
 */
class QueueStrategies {
    private var strategies = mutableListOf<QueueStrategy>()

    operator fun <T: QueueStrategy> T.unaryPlus() = strategies.add(this)
    operator fun <T: QueueStrategy> T.plus(other: QueueStrategy) {
        strategies.add(other)
    }

    fun add(strategy: QueueStrategy) = strategies.add(strategy)

    @SafeVarargs
    fun <E: QueueStrategy> addAll(vararg elements: E) = strategies.addAll(elements)
    fun <E: QueueStrategy> addAll(elements: Collection<E>) = strategies.addAll(elements)
    val size = strategies.size
    fun first() = strategies.first()
    fun any(predicate: (QueueStrategy) -> Boolean) = strategies.any(predicate)
    fun remove(element: QueueStrategy) = strategies.remove(element)
    fun removeAll(elements: Collection<QueueStrategy>) = strategies.removeAll(elements)
    fun clear() = strategies.clear()
}