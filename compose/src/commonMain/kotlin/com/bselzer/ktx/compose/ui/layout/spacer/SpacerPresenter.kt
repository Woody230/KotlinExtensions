package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SpacerPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
) : Presenter<SpacerPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = SpacerPresenter()
    }

    override fun safeMerge(other: SpacerPresenter) = SpacerPresenter(
        modifiers = modifiers.merge(other.modifiers)
    )

    @Composable
    override fun localized() = this
}