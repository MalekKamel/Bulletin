package com.sha.bulletin

import androidx.fragment.app.FragmentActivity
import java.util.*

object QueueManager {
    var queue: Queue<Bulletin> = ArrayDeque()

    fun canQueue(bulletin: Bulletin): Boolean {
        if (BulletinConfig.queueStrategies.any { it.shouldQueue(bulletin, BulletinManager.displayedBulletins) }){
            queue.add(bulletin)
            return true
        }
       return false
    }

    fun showNext(activity: FragmentActivity?) {
        if (queue.isNotEmpty()) queue.remove().showBulletin(activity)
    }

}