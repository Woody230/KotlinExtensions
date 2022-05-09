package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.divider.DividerLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

data class RowLogic(
    /**
     * The [LogicModel] of the divider.
     */
    val divider: DividerLogic? = null
) : LogicModel {
    companion object {
        @Stable
        val Default = RowLogic()
    }
}