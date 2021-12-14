package com.bselzer.ktx.compose.ui.appbar

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
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