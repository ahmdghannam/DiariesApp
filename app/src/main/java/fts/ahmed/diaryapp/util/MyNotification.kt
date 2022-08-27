package fts.ahmed.diaryapp.util

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import fts.ahmed.diaryapp.R

const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "messageExtra"

class MyNotification : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notification =
            NotificationCompat.Builder(context, channelID)
                .setSmallIcon(R.drawable.ic_note)
                .setContentTitle(intent?.getStringExtra(titleExtra).orEmpty())
                .setContentText(intent?.getStringExtra(messageExtra).orEmpty())
                .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(notificationID, notification)


    }
}