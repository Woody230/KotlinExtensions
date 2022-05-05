package com.bselzer.ktx.compose.ui.layout.surface

import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class SurfaceLogic(
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
) : LogicModel