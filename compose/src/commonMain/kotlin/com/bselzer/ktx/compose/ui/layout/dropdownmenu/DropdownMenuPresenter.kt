package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.DpOffset
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class DropdownMenuPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * [DpOffset] to be added to the position of the menu
     */
    val offset: DpOffset = ComposeMerger.dpOffset.default,

    /**
     * The [Presentable] for the icons.
     */
    val icon: IconButtonPresenter = IconButtonPresenter.Default
) : Presenter<DropdownMenuPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = DropdownMenuPresenter()
    }

    override fun safeMerge(other: DropdownMenuPresenter) = DropdownMenuPresenter(
        modifiers = modifiers.merge(other.modifiers),
        offset = ComposeMerger.dpOffset.safeMerge(offset, other.offset),
        icon = icon.merge(other.icon)
    )

    @Composable
    override fun localized() = DropdownMenuPresenter(
        offset = DpOffset.Zero,
    ).merge(this).copy(
        icon = icon.localized()
    )
}