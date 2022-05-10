package com.bselzer.ktx.compose.ui.layout.floatingactionbutton

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class FloatingActionButtonInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] of the text for an expanded FAB.
     */
    val text: TextInteractor? = null,

    /**
     * The [Interactor] of the icon.
     */
    val icon: IconInteractor? = null,

    /**
     * Callback invoked when this FAB is clicked
     */
    val onClick: () -> Unit,
) : Interactor(modifiers)