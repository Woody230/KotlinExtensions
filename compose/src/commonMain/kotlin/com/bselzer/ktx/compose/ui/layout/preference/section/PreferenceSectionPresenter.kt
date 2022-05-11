package com.bselzer.ktx.compose.ui.layout.preference.section

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.description.descriptionTitlePresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.preferenceImagePresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class PreferenceSectionPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presenter] for the image associated with the section content.
     */
    val image: ImagePresenter = ImagePresenter.Default,

    /**
     * The [Presenter] for the title describing the section content.
     */
    val title: TextPresenter = TextPresenter.Default
) : Presenter<PreferenceSectionPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = PreferenceSectionPresenter()
    }

    override fun safeMerge(other: PreferenceSectionPresenter) = PreferenceSectionPresenter(
        modifiers = modifiers.merge(other.modifiers),
        image = image.merge(other.image),
        title = title.merge(other.title)
    )

    @Composable
    override fun localized() = PreferenceSectionPresenter(
        image = preferenceImagePresenter(),
        title = descriptionTitlePresenter()
    ).merge(this).run {
        copy(
            image = image.localized(),
            title = title.localized()
        )
    }
}