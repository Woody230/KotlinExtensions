package com.bselzer.ktx.compose.ui.layout.radiobutton

import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class RadioButtonPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * [RadioButtonColors] that will be used to resolve the background and content color for this RadioButton in different states. See [RadioButtonDefaults.colors].
     */
    val colors: RadioButtonColors = ComposeMerger.radioButtonColors.default,
) : Presenter<RadioButtonPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = RadioButtonPresenter()
    }

    override fun safeMerge(other: RadioButtonPresenter) = RadioButtonPresenter(
        modifiers = modifiers.merge(other.modifiers),
        colors = ComposeMerger.radioButtonColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun localized() = RadioButtonPresenter(
        colors = RadioButtonDefaults.colors()
    ).merge(this)
}