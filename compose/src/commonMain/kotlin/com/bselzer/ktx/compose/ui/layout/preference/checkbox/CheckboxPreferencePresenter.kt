package com.bselzer.ktx.compose.ui.layout.preference.checkbox

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.checkbox.CheckboxPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.PreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class CheckboxPreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val preference: PreferencePresenter = PreferencePresenter.Default,
    val checkbox: CheckboxPresenter = CheckboxPresenter.Default,
) : Presenter<CheckboxPreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = CheckboxPreferencePresenter()
    }

    override fun safeMerge(other: CheckboxPreferencePresenter) = CheckboxPreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        preference = preference.merge(other.preference),
        checkbox = checkbox.merge(other.checkbox)
    )

    @Composable
    override fun localized() = copy(
        preference = preference.localized(),
        checkbox = checkbox.localized()
    )
}