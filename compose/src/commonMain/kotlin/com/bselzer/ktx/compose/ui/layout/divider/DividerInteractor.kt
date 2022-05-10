package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

class DividerInteractor : Interactor() {
    companion object {
        @Stable
        val Default = DividerInteractor()
    }
}