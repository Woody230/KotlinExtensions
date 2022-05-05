package com.bselzer.ktx.compose.ui.layout.dialog

import com.bselzer.ktx.compose.ui.style.Style
import com.bselzer.ktx.function.objects.safeMerge

actual class DialogProperties(
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
) : Style<DialogProperties>() {
    actual constructor() : this(visible = true)

    override fun safeMerge(other: DialogProperties): DialogProperties = DialogProperties(
        visible = visible.safeMerge(other.visible, true),
        undecorated = undecorated.safeMerge(other.undecorated, false),
        transparent = transparent.safeMerge(other.transparent, false),
        resizable = resizable.safeMerge(other.resizable, true),
        enabled = enabled.safeMerge(other.enabled, true),
        focusable = focusable.safeMerge(other.focusable, true)
    )
}