package com.bselzer.ktx.compose.ui.layout.textpreference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.description.descriptionSubtitlePresenter
import com.bselzer.ktx.compose.ui.layout.description.descriptionTitlePresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.preferenceImagePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class TextPreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presenter] for the image.
     */
    val image: ImagePresenter = ImagePresenter.Default,

    /**
     * The [Presentable] for the title.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The [Presentable] for the subtitle.
     */
    val subtitle: TextPresenter = TextPresenter.Default
) : Presenter<TextPreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = TextPreferencePresenter()
    }

    override fun safeMerge(other: TextPreferencePresenter) = TextPreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        image = image.merge(other.image),
        title = title.merge(other.title),
        subtitle = subtitle.merge(other.subtitle)
    )

    @Composable
    override fun localized() = TextPreferencePresenter(
        image = preferenceImagePresenter(),
        title = descriptionTitlePresenter(),
        subtitle = descriptionSubtitlePresenter()
    ).merge(this).run {
        copy(
            image = image.localized(),
            title = title.localized(),
            subtitle = subtitle.localized()
        )
    }
}