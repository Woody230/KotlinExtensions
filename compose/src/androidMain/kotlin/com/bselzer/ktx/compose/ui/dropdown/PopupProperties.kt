package com.bselzer.ktx.compose.ui.dropdown

import androidx.compose.ui.window.SecureFlagPolicy
import com.bselzer.ktx.compose.ui.style.Style

/**
 * Properties used to customize the behavior of a popup.
 */
data class PopupProperties(
    /**
     * Whether the popup is focusable. When true, the popup will receive IME events and key presses, such as when the back button is pressed.
     */
    val focusable: Boolean = false,

    /**
     * Whether the popup can be dismissed by pressing the back button. If true, pressing the back button will call onDismissRequest.
     * Note that focusable must be set to true in order to receive key events such as the back button - if the popup is not focusable then this property does nothing.
     */
    val dismissOnBackPress: Boolean = true,

    /**
     * Whether the popup can be dismissed by clicking outside the popup's bounds. If true, clicking outside the popup will call onDismissRequest.
     */
    val dismissOnClickOutside: Boolean = true,

    /**
     * Policy for setting WindowManager.LayoutParams.FLAG_SECURE on the popup's window.
     */
    val securePolicy: SecureFlagPolicy = SecureFlagPolicy.Inherit,

    /**
     * A flag to check whether to set the systemGestureExclusionRects. The default is true.
     */
    val excludeFromSystemGesture: Boolean = true,

    /**
     * Whether to allow the popup window to extend beyond the bounds of the screen. By default the window is clipped to the screen boundaries.
     * Setting this to false will allow windows to be accurately positioned. The default value is true.
     */
    val clippingEnabled: Boolean = true,

    /**
     * Whether the width of the popup's content should be limited to the platform default, which is smaller than the screen width.
     */
    val usePlatformDefaultWidth: Boolean = false
): Style