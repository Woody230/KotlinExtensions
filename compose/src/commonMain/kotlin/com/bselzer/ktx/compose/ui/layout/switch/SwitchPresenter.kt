package com.bselzer.ktx.compose.ui.layout.switch

import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SwitchPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     *  SwitchColors that will be used to determine the color of the thumb and track in different states. See SwitchDefaults.colors.
     */
    val colors: SwitchColors = ComposeMerger.switchColors.default
) : Presenter<SwitchPresenter>(modifier) {
    companion object {
        @Stable
        val Default = SwitchPresenter()
    }

    override fun safeMerge(other: SwitchPresenter) = SwitchPresenter(
        modifier = modifier.merge(other.modifier),
        colors = ComposeMerger.switchColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun localized() = SwitchPresenter(
        colors = SwitchDefaults.colors()
    ).merge(this)
}