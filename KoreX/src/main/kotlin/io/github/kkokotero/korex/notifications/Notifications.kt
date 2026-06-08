package io.github.kkokotero.korex.notifications

import android.annotation.SuppressLint
import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import io.github.kkokotero.korex.android.Permission
import io.github.kkokotero.korex.android.Permissions
import io.github.kkokotero.korex.android.PermissionManager

data class NotificationChannelSpec(
    val id: String,
    val name: String,
    val description: String? = null,
    val importance: Int = NotificationManager.IMPORTANCE_DEFAULT,
)

data class LocalNotification(
    val id: Int,
    val channel: NotificationChannelSpec,
    val title: String,
    val text: String,
    val smallIcon: Int = android.R.drawable.ic_dialog_info,
    val ongoing: Boolean = false,
)

interface NotificationPublisher {
    fun publish(notification: LocalNotification)
}

class NotificationCenter(
    private val publisher: NotificationPublisher,
) {
    fun channel(id: String, name: String = id, description: String? = null): NotificationChannelHandle =
        NotificationChannelHandle(publisher, NotificationChannelSpec(id = id, name = name, description = description))
}

class NotificationChannelHandle internal constructor(
    private val publisher: NotificationPublisher,
    private val channel: NotificationChannelSpec,
) {
    fun show(text: String, title: String = channel.name, id: Int = text.hashCode().and(0x7fffffff)) {
        publisher.publish(
            LocalNotification(
                id = id,
                channel = channel,
                title = title,
                text = text,
            ),
        )
    }
}

fun notifications(publisher: NotificationPublisher): NotificationCenter = NotificationCenter(publisher)
fun notifications(context: Context, permissionManager: PermissionManager): NotificationCenter =
    NotificationCenter(AndroidNotificationPublisher(context, permissionManager))

class AndroidNotificationPublisher(
    private val context: Context,
    private val permissionManager: PermissionManager,
) : NotificationPublisher {
    @SuppressLint("MissingPermission")
    override fun publish(notification: LocalNotification) {
        permissionManager.request(Permissions.PostNotifications)
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        val manager = NotificationManagerCompat.from(context)
        val channel = NotificationChannel(
            notification.channel.id,
            notification.channel.name,
            notification.channel.importance,
        ).apply {
            description = notification.channel.description
        }
        context.getSystemService(NotificationManager::class.java)?.createNotificationChannel(channel)
        val built = NotificationCompat.Builder(context, notification.channel.id)
            .setSmallIcon(notification.smallIcon)
            .setContentTitle(notification.title)
            .setContentText(notification.text)
            .setOngoing(notification.ongoing)
            .setAutoCancel(!notification.ongoing)
            .build()
        manager.notify(notification.id, built)
    }
}

interface ForegroundNotificationController {
    fun start(id: Int, notification: Notification)
    fun stop(id: Int)
}

class AndroidForegroundNotificationController(
    private val notificationManager: NotificationManager,
) : ForegroundNotificationController {
    override fun start(id: Int, notification: Notification) {
        notificationManager.notify(id, notification)
    }

    override fun stop(id: Int) {
        notificationManager.cancel(id)
    }
}

fun notificationPermission(): Permission = Permissions.PostNotifications
