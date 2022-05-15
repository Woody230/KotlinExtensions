package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class CenteredTextPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] of the text.
     */
    val text: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] of the spacing between the starting and ending text.
     */
    val spacer: SpacerPresenter = SpacerPresenter.Default
) : Presenter<CenteredTextPresenter>(modifier) {
    companion object {
        @Stable
        val Default = CenteredTextPresenter()
    }

    override fun safeMerge(other: CenteredTextPresenter) = CenteredTextPresenter(
        modifier = modifier.merge(other.modifier),
        text = text.merge(other.text),
        spacer = spacer.merge(other.spacer)
    )

    @Composable
    override fun localized() = copy(
        text = text.localized(),
        spacer = spacer.localized()
    )
}