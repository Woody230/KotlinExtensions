package com.bselzer.ktx.compose.ui.layout.radiobutton

import androidx.compose.material.RadioButtonColors
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class RadioButtonPresentation(
    /**
     * [RadioButtonColors] that will be used to resolve the background and content color for this RadioButton in different states. See [RadioButtonDefaults.colors].
     */
    val colors: RadioButtonColors = ComposeMerger.radioButtonColors.default,
) : Presenter<RadioButtonPresentation>() {
    companion object {
        @Stable
        val Default = RadioButtonPresentation()
    }

    override fun safeMerge(other: RadioButtonPresentation) = RadioButtonPresentation(
        colors = ComposeMerger.radioButtonColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun createLocalization() = RadioButtonPresentation(
        colors = RadioButtonDefaults.colors()
    )
}