package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerLogic
import com.bselzer.ktx.compose.ui.layout.modifier.Clickable
import com.bselzer.ktx.compose.ui.layout.modifier.Modifiable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class ColumnLogic(
    /**
     * The [LogicModel] of the divider.
     */
    val divider: DividerLogic? = null,

    /**
     * The [Modifiable] click component.
     */
    val clickable: Clickable? = null
) : LogicModel {
    companion object {
        @Stable
        val Default = ColumnLogic()
    }
}