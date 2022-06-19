package com.bselzer.ktx.compose.ui.layout.progress.indicator

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ProgressIndicatorInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The progress of this progress indicator, where 0.0 represents no progress and 1.0 represents full progress.
     * Values outside of this range are coerced into the range.
     *
     * If the progress is null, then it is considered indeterminate.
     */
    val progress: Float? = null
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = ProgressIndicatorInteractor()
    }
}