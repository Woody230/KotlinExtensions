package com.bselzer.ktx.compose.ui

import androidx.compose.runtime.Composable

/**
 * Displays an alert notification with the given [title] and [message].
 * Platform specific characteristics are changed based on the alert [type].
 */
@Composable
expect fun ShowAlert(title: String, message: String, type: AlertType = AlertType.DEFAULT)

/**
 * The type of alert.
 */
enum class AlertType {
    DEFAULT,
    INFO,
    WARNING,
    ERROR
}