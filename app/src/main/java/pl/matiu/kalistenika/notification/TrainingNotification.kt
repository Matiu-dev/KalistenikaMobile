package pl.matiu.kalistenika.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import pl.matiu.kalistenika.R

fun startNotification(context: Context, title: String) {
    with(NotificationManagerCompat.from(context)) {
        if(ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED) {
            return@with
        }
        notify(1, createNotification(context = context, title = title).build())
    }
}

fun createNotification(context: Context, title: String): NotificationCompat.Builder {
    Log.d("notification", "tutaj")
    return NotificationCompat.Builder(context, "1")
        .setSmallIcon(R.drawable.pauseimage3)
        .setContentTitle(title)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
}

fun createNotificationChannel(context: Context) {
    Log.d("notification", "sprawdzenie notyfikacji")
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is not in the Support Library.
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = "name"
        val descriptionText = "description"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("1", name, importance).apply {
            description = descriptionText
        }
        // Register the channel with the system.
        val notificationManager: NotificationManager =
            getSystemService(context, NotificationManager::class.java) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}