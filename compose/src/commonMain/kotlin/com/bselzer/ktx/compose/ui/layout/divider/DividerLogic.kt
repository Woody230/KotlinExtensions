package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class DividerLogic : LogicModel {
    companion object {
        @Stable
        val Default = DividerLogic()
    }
}