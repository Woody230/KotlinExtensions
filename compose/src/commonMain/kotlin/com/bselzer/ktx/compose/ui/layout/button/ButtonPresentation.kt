package com.bselzer.ktx.compose.ui.layout.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.function.objects.safeMerge

data class ButtonPresentation(
    /**
     * [ButtonElevation] used to resolve the elevation for this button in different states. This controls the size of the shadow below the button. Pass null here to disable elevation for this button. See [ButtonDefaults.elevation].
     */
    val elevation: ButtonElevation? = ComposeMerger.buttonElevation.default,

    /**
     * Defines the button's shape as well as its shadow
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * Border to draw around the button
     */
    val border: BorderStroke? = ComposeMerger.borderStroke.default,

    /**
     * [ButtonColors] that will be used to resolve the background and content color for this button in different states. See [ButtonDefaults.buttonColors].
     */
    val colors: ButtonColors = ComposeMerger.buttonColors.default,

    /**
     * The spacing values to apply internally between the container and the content
     */
    val contentPadding: PaddingValues = ComposeMerger.paddingValues.default,

    /**
     * The type of button.
     */
    val type: ButtonType = ButtonType.DEFAULT
) : Presenter<ButtonPresentation>() {
    companion object {
        @Stable
        val Default = ButtonPresentation()
    }

    override fun safeMerge(other: ButtonPresentation) = ButtonPresentation(
        elevation = ComposeMerger.buttonElevation.nullMerge(elevation, other.elevation),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        border = ComposeMerger.borderStroke.nullMerge(border, other.border),
        colors = ComposeMerger.buttonColors.safeMerge(colors, other.colors),
        contentPadding = ComposeMerger.paddingValues.safeMerge(contentPadding, other.contentPadding),
        type = type.safeMerge(other.type, ButtonType.DEFAULT)
    )

    @Composable
    override fun localized() = when (type) {
        ButtonType.OUTLINED -> outlinedButtonPresentation()
        ButtonType.TEXT -> textButtonPresentation()
        else -> containedButtonPresentation()
    }.merge(this)
}

/**
 * The [ButtonPresentation] for a contained button.
 *
 * Contained buttons are high-emphasis, distinguished by their use of elevation and fill.
 * They contain actions that are primary to your app.
 */
@Composable
fun containedButtonPresentation(): ButtonPresentation = ButtonPresentation(
    elevation = ButtonDefaults.elevation(),
    shape = MaterialTheme.shapes.small,
    border = null,
    colors = ButtonDefaults.buttonColors(),
    contentPadding = ButtonDefaults.ContentPadding
)

/**
 * The [ButtonPresentation] for an outlined button.
 *
 * Outlined buttons are medium-emphasis buttons.
 * They contain actions that are important, but aren't the primary action in an app.
 */
@Composable
fun outlinedButtonPresentation(): ButtonPresentation = ButtonPresentation(
    elevation = null,
    shape = MaterialTheme.shapes.small,
    border = ButtonDefaults.outlinedBorder,
    colors = ButtonDefaults.outlinedButtonColors(),
    contentPadding = ButtonDefaults.ContentPadding
)

/**
 * The [ButtonPresentation] for a text button.
 *
 * Text buttons are typically used for less-pronounced actions, including those located in dialogs and cards.
 * In cards, text buttons help maintain an emphasis on card content.
 */
@Composable
fun textButtonPresentation(): ButtonPresentation = ButtonPresentation(
    elevation = null,
    shape = MaterialTheme.shapes.small,
    border = null,
    colors = ButtonDefaults.textButtonColors(),
    contentPadding = ButtonDefaults.TextButtonContentPadding
)