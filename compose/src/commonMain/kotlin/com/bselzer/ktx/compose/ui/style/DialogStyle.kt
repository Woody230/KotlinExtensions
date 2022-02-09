package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.function.objects.merge

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
    val shape: Shape? = null,

    /**
     * The color of the dialog background.
     */
    val backgroundColor: Color? = null,

    /**
     * The color of the dialog content.
     */
    val contentColor: Color? = null,

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
}

