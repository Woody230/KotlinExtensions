package com.bselzer.ktx.compose.ui.layout.preference.switch

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.PreferencePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.switch.SwitchPresenter

data class SwitchPreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val preference: PreferencePresenter = PreferencePresenter.Default,
    val switch: SwitchPresenter = SwitchPresenter.Default,
) : Presenter<SwitchPreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = SwitchPreferencePresenter()
    }

    override fun safeMerge(other: SwitchPreferencePresenter) = SwitchPreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        preference = preference.merge(other.preference),
        switch = switch.merge(other.switch)
    )

    @Composable
    override fun localized() = copy(
        preference = preference.localized(),
        switch = switch.localized()
    )
}