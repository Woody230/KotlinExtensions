package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class DropdownMenuInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * Whether the menu is currently open and visible to the user
     */
    val expanded: Boolean = true,

    /**
     * Whether the popup is focusable. When true, the popup will receive IME events and key presses, such as when the back button is pressed.
     */
    val focusable: Boolean = true,

    /**
     * The [Interactor]s for the icons.
     */
    val icons: Collection<IconButtonInteractor>,

    /**
     * Called when the user requests to dismiss the menu, such as by tapping outside the menu's bounds
     */
    val onDismissRequest: () -> Unit,
) : Interactor(modifiers)