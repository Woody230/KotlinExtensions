package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.runtime.Composable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

class IconButtonPresentation : Presenter<IconButtonPresentation>() {
    @Composable
    override fun safeMerge(other: IconButtonPresentation) = other
}