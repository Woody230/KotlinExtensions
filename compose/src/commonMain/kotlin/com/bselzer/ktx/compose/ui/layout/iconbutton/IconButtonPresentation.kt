package com.bselzer.ktx.compose.ui.layout.iconbutton

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.icon.IconPresentation
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class IconButtonPresentation(
    /**
     * The [PresentationModel] for the icon.
     */
    val icon: IconPresentation = IconPresentation()
) : Presenter<IconButtonPresentation>() {
    companion object {
        @Stable
        val Default = IconButtonPresentation()
    }

    override fun safeMerge(other: IconButtonPresentation) = IconButtonPresentation(
        icon = icon.merge(other.icon)
    )

    @Composable
    override fun localized() = copy(icon = icon.localized())
}