package com.bselzer.ktx.compose.ui.layout.tristatecheckbox

import androidx.compose.material.CheckboxColors
import androidx.compose.material.CheckboxDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class TriStateCheckboxPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * CheckboxColors that will be used to determine the color of the checkmark / box / border in different states. See CheckboxDefaults.colors.
     */
    val colors: CheckboxColors = ComposeMerger.checkboxColors.default
) : Presenter<TriStateCheckboxPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = TriStateCheckboxPresenter()
    }

    override fun safeMerge(other: TriStateCheckboxPresenter) = TriStateCheckboxPresenter(
        modifiers = modifiers.merge(other.modifiers),
        colors = ComposeMerger.checkboxColors.safeMerge(colors, other.colors)
    )

    @Composable
    override fun localized() = TriStateCheckboxPresenter(
        colors = CheckboxDefaults.colors()
    ).merge(this)
}