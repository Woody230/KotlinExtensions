package com.bselzer.ktx.compose.ui.layout.appbar.bottom

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BottomAppBarInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the navigation icon.
     */
    val navigation: IconButtonInteractor? = null,

    /**
     * The [Interactor] for the action icons.
     */
    val actions: List<IconButtonInteractor> = emptyList(),

    /**
     * The [Interactor] for the dropdown icon.
     */
    val dropdown: IconInteractor? = null,
) : Interactor(modifier)