package com.bselzer.ktx.compose.ui.layout.switch

import androidx.compose.material.SwitchColors
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SwitchPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     *  SwitchColors that will be used to determine the color of the thumb and track in different states. See SwitchDefaults.colors.
     */
    val colors: SwitchColors = ComposeMerger.switchColors.default
) : Presenter<SwitchPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = SwitchPresenter()
    }

    override fun safeMerge(other: SwitchPresenter) = SwitchPresenter(
        modifiers = modifiers.merge(other.modifiers),
        colors = ComposeMerger.switchColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun localized() = SwitchPresenter(
        colors = SwitchDefaults.colors()
    ).merge(this)
}