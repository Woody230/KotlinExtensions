package com.bselzer.ktx.compose.ui.appbar

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext

/**
 * Lays out an icon for navigating up to another activity.
 *
 * @param destination the destination activity
 * @param intent the block for populating an [Intent]
 */
@Composable
fun Activity.UpNavigationIcon(destination: Class<out Activity>, intent: Intent.() -> Unit = {}) {
    val context = LocalContext.current
    UpNavigationIcon {
        val navigate = Intent(context, destination)
        intent(navigate)
        navigateUpTo(navigate)
    }
}

/**
 * Lays out a dropdown menu for an app bar.
 *
 * @param initial whether the dropdown is initially expanded
 * @param icons the icons to lay out in the dropdown menu
 */
@Composable
fun DropdownMenuIcon(
    initial: Boolean = false,
    icons: (@Composable ((Boolean) -> Unit) -> Unit)?
) {
    var isExpanded by remember { mutableStateOf(initial) }
    icons?.let {
        Box {
            DropdownIcon { isExpanded = it }
            DropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
                icons { isExpanded = it }
            }
        }
    }
}