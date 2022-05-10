package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

class BoxInteractor : Interactor() {
    companion object {
        @Stable
        val Default = BoxInteractor()
    }
}