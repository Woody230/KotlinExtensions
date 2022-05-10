package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.Interactor

class BadgeInteractor : Interactor() {
    companion object {
        @Stable
        val Default = BadgeInteractor()
    }
}