package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ColumnInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] of the divider. If it is not null, then the divider will be used.
     */
    val divider: DividerInteractor? = null,
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = ColumnInteractor()
    }
}