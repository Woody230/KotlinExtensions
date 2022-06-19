package com.bselzer.ktx.compose.ui.layout.preference.text

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.textInteractor

data class TextPreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the image.
     */
    val image: ImageInteractor,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor,

    /**
     * The [Interactor] for the subtitle.
     */
    val subtitle: TextInteractor
) : Interactor(modifier) {
    constructor(
        title: String,
        subtitle: String,
        painter: Painter,
        contentDescription: String? = null
    ) : this(
        image = ImageInteractor(painter = painter, contentDescription = contentDescription),
        title = title.textInteractor(),
        subtitle = subtitle.textInteractor()
    )
}