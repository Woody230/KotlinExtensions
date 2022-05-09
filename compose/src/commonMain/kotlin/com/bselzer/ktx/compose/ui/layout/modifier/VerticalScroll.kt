package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier

data class VerticalScroll(
    /**
     *  State of the scroll
     */
    val state: ScrollState,

    /**
     * Whether or not scrolling via touch input is enabled
     */
    val enabled: Boolean = true,

    /**
     * logic describing fling behavior when drag has finished with velocity.
     * If null, default from ScrollableDefaults.flingBehavior will be used.
     */
    val flingBehavior: FlingBehavior? = null,

    /**
     * Reverse the direction of scrolling, when true, 0 ScrollState.value will mean right, when false, 0 ScrollState.value will mean left
     */
    val reverseScrolling: Boolean = false,
) : Modifiable {
    override val modifier: Modifier = Modifier.verticalScroll(
        state = state,
        enabled = enabled,
        flingBehavior = flingBehavior,
        reverseScrolling = reverseScrolling
    )
}