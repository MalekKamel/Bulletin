package com.sha.bulletin

internal object DuplicateManager {

    fun tryIgnore(bulletin: Bulletin): Boolean {
        val ignore = bulletin.duplicateStrategy.shouldIgnore(bulletin, BulletinManager.displayedBulletins)
        if (!ignore) return false
        tryQue(bulletin)
        return true
    }

    private fun tryQue(bulletin: Bulletin) {
        when(bulletin.duplicateStrategy.onIgnoreStrategy) {
            IgnoreDuplicateStrategy.DROP -> { bulletin.status = BulletinStatus.IGNORED }
            IgnoreDuplicateStrategy.QUEUE -> { QueueManager.tryQueue(bulletin) }
        }
    }

}