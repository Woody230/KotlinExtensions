package com.bselzer.ktx.compose.ui.layout.appbar.action

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

interface ActionPolicy {
    val maxActions: Int
        @Composable
        get

    companion object : ActionPolicy by SystemActionPolicy()
}

/**
 * @see <a href="https://cs.android.com/androidx/platform/frameworks/support/+/androidx-main:appcompat/appcompat/src/main/java/androidx/appcompat/view/ActionBarPolicy.java;l=42?q=actionbarpolicy&sq=">ActionBarPolicy.java</a>
 */
internal abstract class AdjustableActionPolicy : ActionPolicy {
    protected abstract val applicationSize: DpSize
        @Composable
        get

    protected open val minimumApplicationSize: DpSize
        @Composable
        get() = DpSize.Unspecified

    override val maxActions: Int
        @Composable
        get() = when {
            // For values-w600dp, values-sw600dp and values-xlarge.
            minimumApplicationSize.width > 600.dp -> 5
            applicationSize.width > 600.dp -> 5
            applicationSize.width > 960.dp && applicationSize.height > 720.dp -> 5
            applicationSize.width > 720.dp && applicationSize.height > 960.dp -> 5

            // For values-w500dp and values-large.
            applicationSize.width >= 500.dp -> 4
            applicationSize.width > 640.dp && applicationSize.height > 480.dp -> 4
            applicationSize.width > 480.dp && applicationSize.height > 640.dp -> 4

            // For values-w360dp.
            applicationSize.width >= 360.dp -> 3

            else -> 2
        }
}

internal expect class SystemActionPolicy() : AdjustableActionPolicy