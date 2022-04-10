package com.bselzer.ktx.compose.ui.dialog

import androidx.compose.ui.window.SecureFlagPolicy
import com.bselzer.ktx.compose.ui.style.Style
import com.bselzer.ktx.function.objects.safeMerge

actual class DialogProperties(
    /**
     * Whether the dialog can be dismissed by pressing the back button. If true, pressing the back button will call onDismissRequest.
     */
    val dismissOnBackPress: Boolean = true,

    /**
     * Whether the dialog can be dismissed by clicking outside the dialog's bounds. If true, clicking outside the dialog will call onDismissRequest.
     */
    val dismissOnClickOutside: Boolean = true,

    /**
     * Policy for setting WindowManager.LayoutParams.FLAG_SECURE on the dialog's window.
     */
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,

    /**
     * Whether the width of the dialog's content should be limited to the platform default, which is smaller than the screen width.
     */
    val usePlatformDefaultWidth: Boolean = true,
) : Style<DialogProperties>() {
    actual constructor() : this(dismissOnBackPress = true)

    override fun safeMerge(other: DialogProperties): DialogProperties = DialogProperties(
        dismissOnBackPress = dismissOnBackPress.safeMerge(other.dismissOnBackPress, true),
        dismissOnClickOutside = dismissOnClickOutside.safeMerge(other.dismissOnClickOutside, true),
        securePolicy = securePolicy.safeMerge(other.securePolicy, SecureFlagPolicy.Inherit),
        usePlatformDefaultWidth = usePlatformDefaultWidth.safeMerge(other.usePlatformDefaultWidth, true)
    )
}