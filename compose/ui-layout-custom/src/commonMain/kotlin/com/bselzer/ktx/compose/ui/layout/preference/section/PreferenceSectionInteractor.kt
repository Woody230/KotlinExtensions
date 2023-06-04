package com.bselzer.ktx.compose.ui.layout.preference.section

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.textInteractor

data class PreferenceSectionInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the image associated with the section content.
     */
    val image: ImageInteractor,

    /**
     * The [Interactor] for the title describing the section content.
     */
    val title: TextInteractor
) : Interactor(modifier) {
    constructor(
        title: String,
        painter: Painter,
        contentDescription: String? = null
    ) : this(
        image = ImageInteractor(
            painter = painter,
            contentDescription = contentDescription
        ),
        title = title.textInteractor()
    )
}