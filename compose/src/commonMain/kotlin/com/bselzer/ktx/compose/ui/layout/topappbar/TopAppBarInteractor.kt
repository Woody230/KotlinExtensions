package com.bselzer.ktx.compose.ui.layout.topappbar

import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class TopAppBarInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor,

    /**
     * The [Interactor] for the navigation icon.
     */
    val navigation: IconButtonInteractor? = null,

    /**
     * The [Interactor] for the action icons.
     */
    val actions: Collection<IconButtonInteractor> = emptyList()
) : Interactor(modifiers)