package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.ui.graphics.vector.ImageVector
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class IconInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * [ImageVector] to draw inside this icon
     */
    val imageVector: ImageVector,

    /**
     * Text used by accessibility services to describe what this icon represents.
     * This should always be provided unless this icon is used for decorative purposes, and does not represent a meaningful action that a user can take.
     * This text should be localized, such as by using androidx.compose.ui.res.stringResource or similar
     */
    val contentDescription: String?,
) : Interactor(modifiers)