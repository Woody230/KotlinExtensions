package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SpacerPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
) : Presenter<SpacerPresenter>(modifier) {
    companion object {
        @Stable
        val Default = SpacerPresenter()
    }

    override fun safeMerge(other: SpacerPresenter) = SpacerPresenter(
        modifier = modifier.merge(other.modifier)
    )

    @Composable
    override fun localized() = this
}