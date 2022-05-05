package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.runtime.Stable

internal class FlingBehaviorMerger : ComponentMerger<FlingBehavior> {
    override val default: FlingBehavior = Default

    companion object {
        @Stable
        val Default = object : FlingBehavior {
            override suspend fun ScrollScope.performFling(initialVelocity: Float): Float = 0f
        }
    }
}