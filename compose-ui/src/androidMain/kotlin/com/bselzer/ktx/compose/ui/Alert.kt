package com.bselzer.ktx.compose.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Displays an alert notification with the given [title] and [message].
 * Platform specific characteristics are changed based on the alert [type].
 */
@Composable
actual fun ShowAlert(title: String, message: String, type: AlertType) = Toast.makeText(
    // TODO title is ignored since toast length does not provide much room
    LocalContext.current,
    message,
    when (type) {
        AlertType.DEFAULT -> Toast.LENGTH_SHORT
        AlertType.INFO -> Toast.LENGTH_SHORT
        AlertType.WARNING -> Toast.LENGTH_SHORT
        AlertType.ERROR -> Toast.LENGTH_LONG
    }
).show()