package com.bselzer.ktx.compose.ui.notification.alert

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.window.Notification
import androidx.compose.ui.window.TrayState
import com.bselzer.ktx.compose.ui.notification.alert.AlertType.*

/**
 * CompositionLocal containing the tray state for sending notifications.
 */
val LocalTrayState: ProvidableCompositionLocal<TrayState> = compositionLocalOf { TrayState() }

/**
 * Displays an alert notification with the given [title] and [message].
 * Platform specific characteristics are changed based on the alert [type].
 */
@Composable
actual fun ShowAlert(title: String, message: String, type: AlertType) {
    val notification = Notification(
        title = title,
        message = message,
        type = when (type) {
            DEFAULT -> Notification.Type.None
            INFO -> Notification.Type.Info
            WARNING -> Notification.Type.Warning
            ERROR -> Notification.Type.Error
        }
    )

    LocalTrayState.current.sendNotification(notification)
}