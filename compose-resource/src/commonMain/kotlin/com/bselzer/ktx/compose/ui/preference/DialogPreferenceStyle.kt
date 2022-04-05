package com.bselzer.ktx.compose.ui.preference

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.dialog.AlertDialogStyle
import com.bselzer.ktx.compose.ui.dialog.LocalAlertDialogStyle
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred DialogPreferenceStyle that will be used by DialogPreference components by default.
 */
val LocalDialogPreferenceStyle: ProvidableCompositionLocal<DialogPreferenceStyle> = compositionLocalOf { DialogPreferenceStyle.Default }

/**
 * The style arguments associated with an DialogPreference composable.
 */
data class DialogPreferenceStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The style of the preference.
     */
    val preferenceStyle: SimplePreferenceStyle = SimplePreferenceStyle.Default,

    /**
     * The style of the button.
     */
    val buttonStyle: ButtonStyle = ButtonStyle.Default,

    /**
     * The style of the button's text.
     */
    val buttonTextStyle: WordStyle = WordStyle.Default,

    /**
     * The style of the dialog.
     */
    val dialogStyle: AlertDialogStyle = AlertDialogStyle.Default,

    /**
     * The style of the dialog's text.
     */
    val dialogTextStyle: WordStyle = WordStyle.Default,

    /**
     * The spacing between components in the dialog.
     */
    val dialogSpacing: Dp = PreferenceSpacing
): ModifiableStyle<DialogPreferenceStyle> {
    companion object {
        @Stable
        val Default = DialogPreferenceStyle()
    }

    override fun merge(other: DialogPreferenceStyle?): DialogPreferenceStyle = if (other == null) this else DialogPreferenceStyle(
        modifier = modifier.then(other.modifier),
        preferenceStyle = preferenceStyle.merge(other.preferenceStyle),
        buttonStyle = buttonStyle.merge(other.buttonStyle),
        buttonTextStyle = buttonTextStyle.merge(other.buttonTextStyle),
        dialogStyle = dialogStyle.merge(other.dialogStyle),
        dialogTextStyle = dialogTextStyle.merge(other.dialogTextStyle),
        dialogSpacing = dialogSpacing.safeMerge(other.dialogSpacing, PreferenceSpacing)
    )

    @Composable
    override fun localized(): DialogPreferenceStyle = DialogPreferenceStyle(
        preferenceStyle = LocalSimplePreferenceStyle.current,
        buttonStyle = LocalButtonStyle.current,
        buttonTextStyle = LocalWordStyle.current,
        dialogStyle = LocalAlertDialogStyle.current,
        dialogTextStyle = LocalWordStyle.current
    ).merge(this)
}
