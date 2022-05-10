package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class RowInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] of the divider.
     */
    val divider: DividerInteractor? = null,
) : Interactor(modifiers) {
    companion object {
        @Stable
        val Default = RowInteractor()
    }
}