package com.bselzer.ktx.compose.ui.layout.description

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class DescriptionPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presentable] for the container holding the title and subtitle.
     */
    val container: ColumnPresenter = ColumnPresenter.Default,

    /**
     * The [Presentable] for the title.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] for the subtitle.
     */
    val subtitle: TextPresenter = TextPresenter.Default
) : Presenter<DescriptionPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = DescriptionPresenter()
    }

    override fun safeMerge(other: DescriptionPresenter) = DescriptionPresenter(
        modifiers = modifiers.merge(other.modifiers),
        container = container.merge(other.container),
        title = title.merge(other.title),
        subtitle = subtitle.merge(other.subtitle)
    )

    @Composable
    override fun localized() = DescriptionPresenter(
        title = descriptionTitlePresentation(),
        subtitle = descriptionSubtitlePresentation()
    ).merge(this).run {
        copy(
            container = container.localized(),
            title = title.localized(),
            subtitle = subtitle.localized()
        )
    }
}

/**
 * The [Presentable] for the title of a description.
 */
@Composable
fun descriptionTitlePresentation() = TextPresenter(
    fontWeight = FontWeight.Bold,
    overflow = TextOverflow.Visible,
    textStyle = MaterialTheme.typography.subtitle1
)

/**
 * The [Presentable] for the subtitle of a description.
 */
@Composable
fun descriptionSubtitlePresentation() = TextPresenter(
    overflow = TextOverflow.Ellipsis,
    textStyle = MaterialTheme.typography.subtitle2
)