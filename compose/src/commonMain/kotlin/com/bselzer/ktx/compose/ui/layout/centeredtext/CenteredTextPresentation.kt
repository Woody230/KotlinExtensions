package com.bselzer.ktx.compose.ui.layout.centeredtext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerPresentation
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class CenteredTextPresentation(
    /**
     * The [PresentationModel] of the text.
     */
    val text: TextPresentation = TextPresentation.Default,

    /**
     * The [PresentationModel] of the spacing between the starting and ending text.
     */
    val spacer: SpacerPresentation = SpacerPresentation.Default
) : Presenter<CenteredTextPresentation>() {
    companion object {
        @Stable
        val Default = CenteredTextPresentation()
    }

    override fun safeMerge(other: CenteredTextPresentation) = CenteredTextPresentation(
        text = text.merge(other.text),
        spacer = spacer.merge(other.spacer)
    )

    @Composable
    override fun localized() = CenteredTextPresentation(
        spacer = SpacerPresentation(size = 5.dp)
    ).merge(this).run {
        copy(
            text = text.localized(),
            spacer = spacer.localized()
        )
    }
}