package com.bselzer.ktx.compose.ui.layout.checkbox

import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class CheckboxPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * CheckboxColors that will be used to determine the color of the checkmark / box / border in different states. See CheckboxDefaults.colors.
     */
    val colors: CheckboxColors = ComposeMerger.checkboxColors.default
) : Presenter<CheckboxPresenter>(modifier) {
    companion object {
        @Stable
        val Default = CheckboxPresenter()
    }

    override fun safeMerge(other: CheckboxPresenter) = CheckboxPresenter(
        modifier = modifier.merge(other.modifier),
        colors = ComposeMerger.checkboxColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun localized() = CheckboxPresenter(
        colors = CheckboxDefaults.colors()
    ).merge(this)
}