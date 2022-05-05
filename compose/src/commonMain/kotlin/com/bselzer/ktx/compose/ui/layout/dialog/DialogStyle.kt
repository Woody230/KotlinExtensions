package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.DefaultShape
import com.bselzer.ktx.compose.ui.layout.merge.merge
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.compose.ui.style.StyleProvider.Companion.provider

/**
 * Properties used to customize the behavior of a Dialog.
 */
expect class DialogProperties(): Style<DialogProperties>

/**
 * CompositionLocal containing the preferred AlertDialogStyle that will be used by AlertDialog components by default.
 */
val LocalAlertDialogStyle: StyleProvider<AlertDialogStyle> = compositionLocalOf { AlertDialogStyle.Default }.provider()

/**
 * The style arguments associated with an AlertDialog composable.
 */
data class AlertDialogStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The shape of the dialog.
     */
    val shape: Shape = DefaultShape,

    /**
     * The color of the dialog background.
     */
    val backgroundColor: Color = Color.Unspecified,

    /**
     * The color of the dialog content.
     */
    val contentColor: Color = Color.Unspecified,

    /**
     * Properties used to customize the behavior of a Dialog.
     */
    val properties: DialogProperties = DialogProperties()
) : ModifierStyle<AlertDialogStyle>() {
    companion object {
        @Stable
        val Default = AlertDialogStyle()
    }

    override fun safeMerge(other: AlertDialogStyle): AlertDialogStyle = AlertDialogStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        backgroundColor = backgroundColor.merge(other.backgroundColor),
        contentColor = contentColor.merge(other.contentColor),
        properties = properties.with(other.properties)
    )

    @Composable
    override fun localized(): AlertDialogStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        AlertDialogStyle(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor)
        ).safeMerge(this)
    }

    override fun modify(modifier: Modifier): AlertDialogStyle = copy(modifier = modifier)
}

