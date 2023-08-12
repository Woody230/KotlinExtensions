package com.bselzer.ktx.compose.ui.layout.appbar.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.ApplicationSize

interface ActionPolicy {
    val maxActions: Int
        @Composable
        get

    companion object : ActionPolicy by AdjustableActionPolicy()
}

/**
 * @see <a href="https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:appcompat/appcompat/src/main/java/androidx/appcompat/view/ActionBarPolicy.java;l=42?q=actionbarpolicy&sq=">ActionBarPolicy.java</a>
 */
internal class AdjustableActionPolicy : ActionPolicy {
    override val maxActions: Int
        @Composable
        get() {
            val currentSize = ApplicationSize.current
            val minimumSize = ApplicationSize.minimum
            return when {
                // For values-w600dp, values-sw600dp and values-xlarge.
                minimumSize.width > 600.dp -> 5
                currentSize.width > 600.dp -> 5
                currentSize.width > 960.dp && currentSize.height > 720.dp -> 5
                currentSize.width > 720.dp && currentSize.height > 960.dp -> 5

                // For values-w500dp and values-large.
                currentSize.width >= 500.dp -> 4
                currentSize.width > 640.dp && currentSize.height > 480.dp -> 4
                currentSize.width > 480.dp && currentSize.height > 640.dp -> 4

                // For values-w360dp.
                currentSize.width >= 360.dp -> 3

                else -> 2
            }
        }
}