package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable

/**
 * Displays a navigation icon for an app bar.
 *
 * @param onClick the on-click handler
 */
@Composable
fun NavigationIcon(onClick: () -> Unit) = IconButton(onClick = onClick) {
    Icon(Icons.Filled.ArrowBack, contentDescription = "Up")
}