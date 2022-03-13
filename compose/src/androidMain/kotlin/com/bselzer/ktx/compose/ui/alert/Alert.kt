package com.bselzer.ktx.compose.ui.alert

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.bselzer.ktx.compose.ui.alert.AlertType.*

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
        DEFAULT -> Toast.LENGTH_SHORT
        INFO -> Toast.LENGTH_SHORT
        WARNING -> Toast.LENGTH_SHORT
        ERROR -> Toast.LENGTH_LONG
    }
).show()