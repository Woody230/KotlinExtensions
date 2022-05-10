package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerPresenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class CenteredTextPresenter(
    /**
     * The [Presentable] of the text.
     */
    val text: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] of the spacing between the starting and ending text.
     */
    val spacer: SpacerPresenter = SpacerPresenter.Default
) : Presenter<CenteredTextPresenter>() {
    companion object {
        @Stable
        val Default = CenteredTextPresenter()
    }

    override fun safeMerge(other: CenteredTextPresenter) = CenteredTextPresenter(
        text = text.merge(other.text),
        spacer = spacer.merge(other.spacer)
    )

    @Composable
    override fun localized() = CenteredTextPresenter(
        spacer = SpacerPresenter(size = 5.dp)
    ).merge(this).run {
        copy(
            text = text.localized(),
            spacer = spacer.localized()
        )
    }
}