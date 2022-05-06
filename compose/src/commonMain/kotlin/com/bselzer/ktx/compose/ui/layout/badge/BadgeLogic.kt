package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.LogicModel

class BadgeLogic : LogicModel {
    companion object {
        @Stable
        val Default = BadgeLogic()
    }
}