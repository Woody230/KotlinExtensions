package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable

/**
 * Displays a navigation icon for going up the hierarchy.
 *
 * @param onClick the on-click handler
 */
@Composable
fun UpNavigationIcon(onClick: () -> Unit) = IconButton(onClick = onClick) {
    Icon(Icons.Filled.ArrowBack, contentDescription = "Up")
}

/**
 * Displays a navigation icon for showing a drawer.
 *
 * @param enabled whether the icon is clickable
 * @param onClick the on-click handler
 */
@Composable
fun DrawerNavigationIcon(enabled: Boolean = true, onClick: () -> Unit) = IconButton(enabled = enabled, onClick = onClick) {
    Icon(Icons.Filled.Menu, contentDescription = "Menu")
}