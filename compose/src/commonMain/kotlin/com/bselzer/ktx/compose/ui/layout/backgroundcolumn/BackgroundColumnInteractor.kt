package com.bselzer.ktx.compose.ui.layout.backgroundcolumn

import androidx.compose.ui.graphics.painter.Painter
import com.bselzer.ktx.compose.ui.layout.box.BoxInteractor
import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BackgroundColumnInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the container holding the [background] image and [column].
     */
    val container: BoxInteractor = BoxInteractor.Default,

    /**
     * The [Interactor] for the background image.
     */
    val background: ImageInteractor,

    val column: ColumnInteractor = ColumnInteractor.Default
) : Interactor(modifiers)

fun backgroundImageInteractor(painter: Painter) = ImageInteractor(
    painter = painter,
    contentDescription = null
)