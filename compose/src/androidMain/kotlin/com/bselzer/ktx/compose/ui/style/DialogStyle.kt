package com.bselzer.ktx.compose.ui.style

import androidx.compose.ui.window.SecureFlagPolicy
import com.bselzer.ktx.function.objects.merge

actual class DialogProperties(
    /**
     * Whether the dialog can be dismissed by pressing the back button. If true, pressing the back button will call onDismissRequest.
     */
    val dismissOnBackPress: Boolean? = null,

    /**
     * Whether the dialog can be dismissed by clicking outside the dialog's bounds. If true, clicking outside the dialog will call onDismissRequest.
     */
    val dismissOnClickOutside: Boolean? = null,

    /**
     * Policy for setting WindowManager.LayoutParams.FLAG_SECURE on the dialog's window.
     */
    val securePolicy: SecureFlagPolicy? = null,

    /**
     * Whether the width of the dialog's content should be limited to the platform default, which is smaller than the screen width.
     */
    val usePlatformDefaultWidth: Boolean? = null,
): Style<DialogProperties> {
    actual constructor() : this(dismissOnBackPress = null)

    override fun merge(other: DialogProperties?): DialogProperties = if (other == null) this else DialogProperties(
        dismissOnBackPress = dismissOnBackPress.merge(other.dismissOnBackPress),
        dismissOnClickOutside = dismissOnClickOutside.merge(other.dismissOnClickOutside),
        securePolicy = securePolicy.merge(other.securePolicy),
        usePlatformDefaultWidth = usePlatformDefaultWidth.merge(other.usePlatformDefaultWidth)
    )
}