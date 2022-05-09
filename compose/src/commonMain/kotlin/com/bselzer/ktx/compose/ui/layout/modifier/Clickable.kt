package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.semantics.Role

data class Clickable(
    /**
     * Controls the enabled state. When false, onClick, onLongClick or onDoubleClick won't be invoked
     */
    val enabled: Boolean = true,

    /**
     * Semantic / accessibility label for the onClick action
     */
    val onClickLabel: String? = null,

    /**
     * Semantic / accessibility label for the onLongClick action
     */
    val onLongClickLabel: String? = null,

    /**
     *  The type of user interface element. Accessibility services might use this to describe the element or do customizations
     */
    val role: Role? = null,

    /**
     *  Indication to be shown when modified element is pressed.
     *  Be default, indication from LocalIndication will be used.
     *  Pass null to show no indication, or current value from LocalIndication to show theme default
     */
    val indication: Indication? = null,

    /**
     * MutableInteractionSource that will be used to emit PressInteraction.Press when this clickable is pressed.
     * Only the initial (first) press will be recorded and emitted with MutableInteractionSource.
     */
    val interactionSource: MutableInteractionSource? = null,

    /**
     * Will be called when user long presses on the element
     */
    val onLongClick: (() -> Unit)? = null,

    /**
     * Will be called when user double clicks on the element
     */
    val onDoubleClick: (() -> Unit)? = null,

    /**
     * Will be called when user clicks on the element
     */
    val onClick: () -> Unit,
) : Modifiable {
    @OptIn(ExperimentalFoundationApi::class)
    override val modifier: Modifier = Modifier.composed {
        Modifier.combinedClickable(
            enabled = enabled,
            onClickLabel = onClickLabel,
            onLongClickLabel = onLongClickLabel,
            onLongClick = onLongClick,
            onDoubleClick = onDoubleClick,
            onClick = onClick,
            role = role,
            indication = indication ?: LocalIndication.current,
            interactionSource = interactionSource ?: remember { MutableInteractionSource() }
        )
    }
}