package com.bselzer.ktx.compose.ui.layout.background.image

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.box.BoxInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BackgroundImageInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the container holding the [background] image.
     */
    val container: BoxInteractor = BoxInteractor.Default,

    /**
     * The [Interactor] for the background image.
     */
    val background: ImageInteractor,
) : Interactor(modifier)

/**
 * Creates a [BackgroundImagePresenter] with the given [painter] and no content description.
 */
fun backgroundImageInteractor(painter: Painter) = BackgroundImageInteractor(
    background = ImageInteractor(
        painter = painter,
        contentDescription = null
    )
)