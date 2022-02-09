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
    val visible: Boolean? = null,

    /**
     * Title in the titlebar of the dialog
     */
    val title: String? = null,

    /**
     * Icon in the titlebar of the dialog (for platforms which support this)
     */
    val icon: Painter? = null,

    /**
     * Disables or enables decorations for this window.
     */
    val undecorated: Boolean? = null,

    /**
     * Disables or enables window transparency. Transparency should be set
     * only if window is undecorated, otherwise an exception will be thrown.
     */
    val transparent: Boolean? = null,

    /**
     * Can dialog be resized by the user (application still can resize the dialog
     * changing [state])
     */
    val resizable: Boolean? = null,

    /**
     * Can dialog react to input events
     */
    val enabled: Boolean? = null,

    /**
     * Can dialog receive focus
     */
    val focusable: Boolean? = null
): Style<DialogProperties> {
    actual constructor(): this(state = null)

    override fun merge(other: DialogProperties?): DialogProperties = if (other == null) this else DialogProperties(
        state = state.merge(other.state),
        visible = visible.merge(other.visible),
        title = title.merge(other.title),
        icon = icon.merge(other.icon),
        undecorated = undecorated.merge(other.undecorated),
        transparent = transparent.merge(other.transparent),
        resizable = resizable.merge(other.resizable),
        enabled = enabled.merge(other.enabled),
        focusable = focusable.merge(other.focusable)
    )
}