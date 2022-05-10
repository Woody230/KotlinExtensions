package com.bselzer.ktx.compose.ui.layout.bottomappbar

import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BottomAppBarInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the navigation icon.
     */
    val navigation: IconButtonInteractor? = null,

    /**
     * The [Interactor] for the action icons.
     */
    val actions: List<IconButtonInteractor> = emptyList()
) : Interactor(modifiers)