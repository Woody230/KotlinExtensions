package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ColumnInteractor(
    /**
     * The [Modifiable] components.
     */
    val modifiers: InteractableModifiers? = null,

    /**
     * The [Interactor] of the divider.
     */
    val divider: DividerInteractor? = null,
) : Interactor() {
    companion object {
        @Stable
        val Default = ColumnInteractor()
    }
}