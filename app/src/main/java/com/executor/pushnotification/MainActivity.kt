package com.executor.pushnotification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

lateinit var notificationManager: NotificationManager
lateinit var notificationChannel: NotificationChannel
lateinit var builder: Notification.Builder
private val channelId = "com.executor.pushnotification"
private val description = "Test notification"

class MainActivity : AppCompatActivity() {
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnFirebase = findViewById<Button>(R.id.btnFirebaseClick)
        btnFirebase.setOnClickListener {
            FirebaseMessagingService()
        }
        val btnMobile = findViewById<Button>(R.id.btnMobileClick)
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        btnMobile.setOnClickListener {
            showNotification()
        }
    }

    private fun showNotification() {
        val intent = Intent(this, MainActivity::class.java)

        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val contentView = RemoteViews(packageName, R.layout.activity_main)

//        val imageurl =

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel =
                NotificationChannel(channelId, description, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
//            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = Notification.Builder(this, channelId)
//                .setContent(contentView)
                .setSmallIcon(R.drawable.active)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                    R.drawable.active))

                .setContentTitle("Notification Example")
                .setContentText("This is a test Notification.")
//                .setContentIntent(pendingIntent)
        } else {

            builder = Notification.Builder(this)
//                .setContent(contentView)
                .setSmallIcon(R.drawable.active)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources,
                    R.drawable.active))
                .setContentTitle("Notification Example")
                .setContentText("This is a test Notification.")
//                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())

    }
    fun getBitmapfromUrl(imageUrl: String?): Bitmap? {
        return try {
            val url = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input: InputStream = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: Exception) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            null
        }
    }


}
