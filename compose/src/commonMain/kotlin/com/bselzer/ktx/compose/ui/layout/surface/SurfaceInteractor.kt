package com.bselzer.ktx.compose.ui.layout.surface

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class SurfaceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * Callback to be called when the Surface is clicked
     */
    val onClick: (() -> Unit)? = null,

    /**
     * Semantic / accessibility label for the onClick action
     */
    val onClickLabel: String? = null,

    /**
     * Controls the enabled state of the Surface. When false, this Surface will not be clickable
     */
    val enabled: Boolean = true,
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = SurfaceInteractor()
    }
}