package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class ColumnLogic : LogicModel {
    companion object {
        @Stable
        val Default = ColumnLogic()
    }
}