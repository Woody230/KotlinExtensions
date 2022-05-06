package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

class IconButtonPresentation : Presenter<IconButtonPresentation>() {
    companion object {
        @Stable
        val Default = IconButtonPresentation()
    }

    override fun safeMerge(other: IconButtonPresentation) = other
}