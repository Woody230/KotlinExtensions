package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class SpacerLogic : LogicModel {
    companion object {
        @Stable
        val Default = SpacerLogic()
    }
}