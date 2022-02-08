package com.bselzer.ktx.compose.ui.style

import androidx.compose.ui.window.SecureFlagPolicy
import com.bselzer.ktx.function.objects.merge

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
): Style<DialogProperties> {
    actual constructor() : this(dismissOnBackPress = true)

    override fun merge(other: DialogProperties?): DialogProperties = if (other == null) this else DialogProperties(
        dismissOnBackPress = dismissOnBackPress.merge(other.dismissOnBackPress, true),
        dismissOnClickOutside = dismissOnClickOutside.merge(other.dismissOnClickOutside, true),
        securePolicy = securePolicy.merge(other.securePolicy, SecureFlagPolicy.Inherit),
        usePlatformDefaultWidth = usePlatformDefaultWidth.merge(other.usePlatformDefaultWidth, true)
    )
}