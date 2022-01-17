package com.example.notify

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MainActivity : AppCompatActivity() {
    val CHANNEL_ID_1 = "channel1"
    val CHANNEL_NAME_1 = "channel1"

    val secretID_1 = -1;
    val secretID_2 = -2;

    var NOTIFICATION_ID = 0

    val CHANNEL_ID_2 = "channel2"
    val CHANNEL_NAME_2 = "channel2"

    var Button1: Button? = null;
    var Button2: Button? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Button1 = findViewById(R.id.asa);
        Button2 = findViewById(R.id.asd);

        val title1: String = "It's important"
        val text1: String = "Player's waiting you - dungeon master"
        val title2: String = "Nature"
        val text2: String = "Take care of nature!!"
        createNotificationChannel(CHANNEL_ID_1, CHANNEL_NAME_1, secretID_1, Button1, text1, title1, R.drawable.ic_stat_name, Color.RED)
        createNotificationChannel(CHANNEL_ID_2, CHANNEL_NAME_2, secretID_2, Button2, text2, title2, R.drawable.ic_recyc, Color.GREEN)
    }

    fun createNotificationChannel(CHANNEL_ID: String, CHANNEL_NAME: String, secret: Int,
                                  notificationButton: Button?, text: String, title: String, image: Int, color: Int) {
        var count: Int = 0;
        var flag: Boolean = true;
        val intent = Intent(CHANNEL_ID)
        val pendingIntent = PendingIntent.getBroadcast(this.applicationContext, 0, intent, 0)

        var builder = NotificationCompat.Builder(this, CHANNEL_ID)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT).apply {}
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)

        }

        var notification = NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(title)
                .setSmallIcon(image)
                .setColor(color)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setGroup(CHANNEL_ID)
                .setDeleteIntent(pendingIntent)

        val notificationManager = NotificationManagerCompat.from(this)

        notificationButton?.setOnClickListener {
            if (count > 2)
            {
                if (flag)
                {
                    NotificationManagerCompat.from(this).deleteNotificationChannel(CHANNEL_NAME)
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        var channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT).apply {}
                        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                        manager.createNotificationChannel(channel)
                        flag = false;
                    }
                }
                notification.setContentText("You missed $count messages")
                notificationManager.notify(secret, notification.build())
            }
            else
            {
                notification.setContentText(text)
                notificationManager.notify(NOTIFICATION_ID, notification.build())
            }
            NOTIFICATION_ID++
            count++;
        }
    }
}