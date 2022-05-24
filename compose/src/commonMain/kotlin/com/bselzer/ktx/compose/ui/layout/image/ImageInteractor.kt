package com.bselzer.ktx.compose.ui.layout.image

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class ImageInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The painter to draw the image.
     */
    val painter: Painter,

    /**
     * Text used by accessibility services to describe what this image represents.
     * This should always be provided unless this image is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    val contentDescription: String?
) : Interactor(modifier)