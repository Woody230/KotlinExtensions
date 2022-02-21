package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.style.ModifiableStyle
import com.bselzer.ktx.compose.ui.style.Style
import com.bselzer.ktx.function.objects.nullMerge

/**
 * Properties used to customize the behavior of a Dialog.
 */
expect class DialogProperties(): Style

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
        shape = shape.nullMerge(other.shape),
        backgroundColor = backgroundColor.nullMerge(other.backgroundColor),
        contentColor = contentColor.nullMerge(other.contentColor),
        properties = properties.merge(other.properties)
    )
}

