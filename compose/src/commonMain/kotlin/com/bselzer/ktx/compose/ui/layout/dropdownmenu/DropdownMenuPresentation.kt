package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DropdownMenuPresentation(
    /**
     * [DpOffset] to be added to the position of the menu
     */
    val offset: DpOffset = ComposeMerger.dpOffset.default,

    /**
     * The [PresentationModel] for the icons.
     */
    val icon: IconButtonPresentation = IconButtonPresentation()
) : Presenter<DropdownMenuPresentation>() {
    companion object {
        @Stable
        val Default = DropdownMenuPresentation()
    }

    override fun safeMerge(other: DropdownMenuPresentation) = DropdownMenuPresentation(
        offset = ComposeMerger.dpOffset.safeMerge(offset, other.offset),
        icon = icon.merge(other.icon)
    )

    @Composable
    override fun localized() = DropdownMenuPresentation(
        offset = DpOffset.Zero,
    ).merge(this).copy(
        icon = icon.localized()
    )
}