package com.example.livescorepin

import android.app.Service
import android.content.Intent
import android.os.IBinder

class LiveScoreService : Service() {

    private lateinit var simulator: ScoreSimulator

    override fun onCreate() {
        super.onCreate()

        simulator = ScoreSimulator { match ->
            NotificationHelper.showOrUpdate(this, match)

            if (match.isMatchOver) {
                stopSelf()
            }
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val initialNotification = NotificationHelper.buildNotification(
            this,
            MatchScore(
                leftFlagRes = R.mipmap.ic_launcher,
                rightFlagRes = R.mipmap.ic_launcher,
                leftScore = 0,
                rightScore = 0,
                statusLine1 = "Starting...",
                statusLine2 = "",
                isMatchOver = false
            )
        )
        startForeground(1001, initialNotification)

        simulator.start()

        return START_NOT_STICKY
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)
        // Explicitly tell Android: don't kill this service just because
        // the app task was swiped away from Recents
    }

    override fun onDestroy() {
        super.onDestroy()
        simulator.stop()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
