package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class BoxLogic : LogicModel {
    companion object {
        @Stable
        val Default = BoxLogic()
    }
}