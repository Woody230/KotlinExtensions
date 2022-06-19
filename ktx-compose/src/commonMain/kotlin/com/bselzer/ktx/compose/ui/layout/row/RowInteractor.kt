package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class RowInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] of the divider.
     */
    val divider: DividerInteractor? = null,
) : Interactor(modifier) {
    companion object {
        @Stable
        val Default = RowInteractor()

        @Stable
        val Divided = RowInteractor(divider = DividerInteractor.Default)
    }
}