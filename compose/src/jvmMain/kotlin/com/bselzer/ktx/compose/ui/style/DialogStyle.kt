package com.bselzer.ktx.compose.ui.style

import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.window.DialogState
import com.bselzer.ktx.function.objects.merge

actual class DialogProperties(
    /**
     * The state object to be used to control or observe the dialog's state
     * When size/position is changed by the user, state will be updated.
     * When size/position of the dialog is changed by the application (changing state),
     * the native dialog will update its corresponding properties.
     * If [DialogState.position] is not [WindowPosition.isSpecified], then after the first show on the
     * screen [DialogState.position] will be set to the absolute values.
     */
    val state: DialogState? = null,

    /**
     * Is [Dialog] visible to user.
     * If `false`:
     * - internal state of [Dialog] is preserved and will be restored next time the dialog
     * will be visible;
     * - native resources will not be released. They will be released only when [Dialog]
     * will leave the composition.
     */
    val visible: Boolean = true,

    /**
     * Title in the titlebar of the dialog
     */
    val title: String = "Untitled",

    /**
     * Icon in the titlebar of the dialog (for platforms which support this)
     */
    val icon: Painter? = null,

    /**
     * Disables or enables decorations for this window.
     */
    val undecorated: Boolean = false,

    /**
     * Disables or enables window transparency. Transparency should be set
     * only if window is undecorated, otherwise an exception will be thrown.
     */
    val transparent: Boolean = false,

    /**
     * Can dialog be resized by the user (application still can resize the dialog
     * changing [state])
     */
    val resizable: Boolean = true,

    /**
     * Can dialog react to input events
     */
    val enabled: Boolean = true,

    /**
     * Can dialog receive focus
     */
    val focusable: Boolean = true
): Style<DialogProperties> {
    actual constructor(): this(state = null)

    override fun merge(other: DialogProperties?): DialogProperties = if (other == null) this else DialogProperties(
        state = state.merge(other.state),
        visible = visible.merge(other.visible, true),
        title = title.merge(other.title, "Untitled"),
        icon = icon.merge(other.icon),
        undecorated = undecorated.merge(other.undecorated, false),
        transparent = transparent.merge(other.transparent, false),
        resizable = resizable.merge(other.resizable, true),
        enabled = enabled.merge(other.enabled, true),
        focusable = focusable.merge(other.focusable, true)
    )
}