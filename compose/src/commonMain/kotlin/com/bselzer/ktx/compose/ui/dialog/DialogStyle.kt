package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.style.DefaultShape
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.compose.ui.style.Style
import com.bselzer.ktx.compose.ui.style.merge

/**
 * Properties used to customize the behavior of a Dialog.
 */
expect class DialogProperties(): Style<DialogProperties>

/**
 * CompositionLocal containing the preferred AlertDialogStyle that will be used by AlertDialog components by default.
 */
val LocalAlertDialogStyle: ProvidableCompositionLocal<AlertDialogStyle> = compositionLocalOf { AlertDialogStyle.Default }

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
): ModifiableStyle<AlertDialogStyle> {
    companion object {
        @Stable
        val Default = AlertDialogStyle()
    }

    override fun merge(other: AlertDialogStyle?): AlertDialogStyle = if (other == null) this else AlertDialogStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        backgroundColor = backgroundColor.merge(other.backgroundColor),
        contentColor = contentColor.merge(other.contentColor),
        properties = properties.merge(other.properties)
    )

    @Composable
    override fun localized(): AlertDialogStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        AlertDialogStyle(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor)
        ).merge(this)
    }
}

