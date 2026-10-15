package com.techgptx.jarvis.service
import android.app.*
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.techgptx.jarvis.R
import com.techgptx.jarvis.MainActivity

class JarvisBackgroundService : Service() {
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val channelId = "JARVIS_TOWER_CHANNEL"
        val channel = NotificationChannel(channelId, "JARVIS Tower", NotificationManager.IMPORTANCE_LOW)
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)

        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("JARVIS Tower ON - JAD JI")
            .setContentText("🔴 بیک گراؤنڈ میں چل رہا ہے - پورا موبائل کنٹرول Active")
            .setSmallIcon(R.drawable.orb_idle)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()

        startForeground(1, notification)
        // یہاں سے پورا موبائل کنٹرول شروع
        return START_STICKY
    }
    override fun onBind(intent: Intent?): IBinder? = null
}
