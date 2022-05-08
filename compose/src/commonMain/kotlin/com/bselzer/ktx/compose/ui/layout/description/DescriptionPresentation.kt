package com.bselzer.ktx.compose.ui.layout.description

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation

data class DescriptionPresentation(
    /**
     * The [PresentationModel] for the title.
     */
    val title: TextPresentation = TextPresentation.Default,

    /**
     * The [PresentationModel] for the subtitle.
     */
    val subtitle: TextPresentation = TextPresentation.Default
) : Presenter<DescriptionPresentation>() {
    companion object {
        @Stable
        val Default = DescriptionPresentation()
    }

    override fun safeMerge(other: DescriptionPresentation) = DescriptionPresentation(
        title = title.merge(other.title),
        subtitle = subtitle.merge(other.subtitle)
    )

    @Composable
    override fun localized() = DescriptionPresentation(
        title = descriptionTitlePresentation(),
        subtitle = descriptionSubtitlePresentation()
    ).merge(this).run {
        copy(
            title = title.localized(),
            subtitle = subtitle.localized()
        )
    }
}

/**
 * The [PresentationModel] for the title of a description.
 */
@Composable
fun descriptionTitlePresentation() = TextPresentation(
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Visible,
    textStyle = MaterialTheme.typography.subtitle1
)

/**
 * The [PresentationModel] for the subtitle of a description.
 */
@Composable
fun descriptionSubtitlePresentation() = TextPresentation(
    overflow = TextOverflow.Ellipsis,
    textStyle = MaterialTheme.typography.subtitle2
)