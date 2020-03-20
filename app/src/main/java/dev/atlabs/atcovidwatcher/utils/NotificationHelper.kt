package dev.atlabs.atcovidwatcher.utils
import android.app.Notification.VISIBILITY_PUBLIC
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationCompat
import dev.atlabs.atcovidwatcher.R
import dev.atlabs.atcovidwatcher.ui.MainActivity

class NotificationHelper(private val context: Context) {
    private var mNotificationManager: NotificationManager? = null
    private lateinit var mBuilder: NotificationCompat.Builder
    /**
     * Create and push the notification
     */
    fun createNotification(title: String?, message: String?) {
        /**Creates an explicit intent for an Activity in your app */
        val resultIntent = Intent(context, MainActivity::class.java)
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        val resultPendingIntent = PendingIntent.getActivity(
            context,
            0 /* Request code */, resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        mBuilder = NotificationCompat.Builder(context)
        mBuilder.setSmallIcon(R.mipmap.ic_launcher)
        mBuilder.setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(false)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message))
            .setContentIntent(resultPendingIntent)
        mNotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val notificationChannel = NotificationChannel(
                NotificationHelper.Companion.NOTIFICATION_CHANNEL_ID,
                "NOTIFICATION_CHANNEL_NAME",
                importance
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.vibrationPattern = longArrayOf(
                100,
                200,
                300,
                400,
                500,
                400,
                300,
                200,
                400
            )
            assert(mNotificationManager != null)
            mBuilder.setChannelId(NotificationHelper.Companion.NOTIFICATION_CHANNEL_ID)
            mNotificationManager!!.createNotificationChannel(notificationChannel)
        }
        assert(mNotificationManager != null)
        mNotificationManager!!.notify(0 /* Request Code */, mBuilder.build())
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "10001"
    }
}
