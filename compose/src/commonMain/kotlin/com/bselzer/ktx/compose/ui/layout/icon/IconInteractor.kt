package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class IconInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * [Painter] to draw inside this icon
     */
    val painter: Painter,

    /**
     * Text used by accessibility services to describe what this icon represents.
     * This should always be provided unless this icon is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    val contentDescription: String?,
) : Interactor(modifier)