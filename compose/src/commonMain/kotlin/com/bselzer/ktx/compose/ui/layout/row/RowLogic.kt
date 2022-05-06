package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class RowLogic : LogicModel {
    companion object {
        @Stable
        val Default = RowLogic()
    }
}