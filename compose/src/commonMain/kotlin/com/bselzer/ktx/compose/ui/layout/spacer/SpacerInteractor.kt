package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

class SpacerInteractor : Interactor() {
    companion object {
        @Stable
        val Default = SpacerInteractor()
    }
}