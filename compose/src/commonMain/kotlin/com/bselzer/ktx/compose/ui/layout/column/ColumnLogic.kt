package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerLogic
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiables
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class ColumnLogic(
    /**
     * The [LogicModel] of the divider.
     */
    val divider: DividerLogic? = null,

    /**
     * The [Modifiable] components.
     */
    val modifiers: Modifiables? = null
) : LogicModel {
    companion object {
        @Stable
        val Default = ColumnLogic()
    }
}