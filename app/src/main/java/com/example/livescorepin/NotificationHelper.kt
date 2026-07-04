package com.example.livescorepin

import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat

object NotificationHelper {

    private const val NOTIFICATION_ID = 1001

    fun buildNotification(context: Context, match: MatchScore): Notification {
        val views = RemoteViews(context.packageName, R.layout.notification_card)

        views.setImageViewResource(R.id.leftFlag, match.leftFlagRes)
        views.setImageViewResource(R.id.rightFlag, match.rightFlagRes)
        views.setTextViewText(R.id.leftScore, match.leftScore.toString())
        views.setTextViewText(R.id.rightScore, match.rightScore.toString())
        views.setTextViewText(R.id.statusLine1, match.statusLine1)

        return NotificationCompat.Builder(context, "live_score_channel")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCustomContentView(views)
            .setCustomBigContentView(views)
            .setOngoing(!match.isMatchOver)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    fun showOrUpdate(context: Context, match: MatchScore) {
        val notification = buildNotification(context, match)
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(NOTIFICATION_ID, notification)
    }
}