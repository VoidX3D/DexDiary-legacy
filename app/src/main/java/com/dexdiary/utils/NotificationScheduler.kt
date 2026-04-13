package com.dexdiary.utils

import android.content.Context
import androidx.work.*
import com.dexdiary.workers.NotificationWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {
    fun scheduleDailyReminders() {
        val workManager = WorkManager.getInstance(context)

        // Morning reminder
        val morningRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(workDataOf("type" to "reminder"))
            .setInitialDelay(calculateInitialDelay(8), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "morning_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            morningRequest
        )

        // Night desperate reminder
        val nightRequest = PeriodicWorkRequestBuilder<NotificationWorker>(24, TimeUnit.HOURS)
            .setInputData(workDataOf("type" to "desperate"))
            .setInitialDelay(calculateInitialDelay(21), TimeUnit.MILLISECONDS)
            .build()

        workManager.enqueueUniquePeriodicWork(
            "night_reminder",
            ExistingPeriodicWorkPolicy.UPDATE,
            nightRequest
        )
    }

    private fun calculateInitialDelay(hour: Int): Long {
        // Simple delay calculation for demo
        return TimeUnit.HOURS.toMillis(1) 
    }
}
