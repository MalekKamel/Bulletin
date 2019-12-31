package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import java.util.*

internal object QueueManager {
    var queue: Queue<Bulletin> = ArrayDeque()

    fun canQueue(bulletin: Bulletin): Boolean {
        // don't queue if no bulletin is displayed
        if(!BulletinManager.isAnyDisplayed()) return false

        // check if any strategy can queue the bulletin
        val canQueue = BulletinConfig.queueStrategies.any {
            it.shouldQueue(bulletin, BulletinManager.displayedBulletins)
        }

        if (!canQueue) return false

        queue.add(bulletin)
        return true
    }

    fun showNext(activity: FragmentActivity?) {
        if (queue.isNotEmpty()) queue.remove().showBulletin(activity)
    }

}