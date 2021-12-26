package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Lays out a refresh icon for an app bar.
 *
 * @param onClick the on-click handler
 */
@Composable
fun RefreshIcon(onClick: suspend CoroutineScope.() -> Unit) {
    val scope = rememberCoroutineScope()
    IconButton(onClick = { scope.launch { onClick(scope) } }) {
        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
    }
}

/**
 * Lays out a delete icon for an app bar.
 *
 * @param enabled whether the on-click handler is enabled
 * @param onClick the on-click handler
 */
@Composable
fun DeleteIcon(enabled: Boolean = true, onClick: suspend CoroutineScope.() -> Unit) {
    val scope = rememberCoroutineScope()
    IconButton(enabled = enabled, onClick = { scope.launch { onClick(scope) } }) {
        Icon(Icons.Filled.Delete, contentDescription = "Delete")
    }
}

/**
 * Lays out an icon for displaying a dropdown menu for an app bar.
 */
@Composable
fun DropdownIcon(setExpanded: (Boolean) -> Unit) = IconButton(onClick = { setExpanded(true) }) {
    Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
}