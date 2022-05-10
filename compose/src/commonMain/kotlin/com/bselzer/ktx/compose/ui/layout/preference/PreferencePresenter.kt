package com.bselzer.ktx.compose.ui.layout.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.description.DescriptionPresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class PreferencePresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presenter] for the image.
     */
    val image: ImagePresenter = ImagePresenter.Default,

    /**
     * The [Presenter] for the name and description of the preference.
     */
    val description: DescriptionPresenter = DescriptionPresenter.Default,
) : Presenter<PreferencePresenter>(modifiers) {
    companion object {
        @Stable
        val Default = PreferencePresenter()
    }

    override fun safeMerge(other: PreferencePresenter) = PreferencePresenter(
        modifiers = modifiers.merge(other.modifiers),
        image = image.merge(other.image),
        description = description.merge(other.description)
    )

    @Composable
    override fun localized() = PreferencePresenter(
        image = ImagePresenter(
            modifiers = PresentableModifiers(size = ModularSize(48.dp, 48.dp)),
            contentScale = ContentScale.FillBounds
        )
    ).merge(this).run {
        copy(
            image = image.localized(),
            description = description.localized()
        )
    }
}