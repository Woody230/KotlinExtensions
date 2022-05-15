package com.bselzer.ktx.compose.ui.layout.icontext

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.row.RowInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class IconTextInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] of the container holding the icon and text.
     */
    val container: RowInteractor = RowInteractor.Default,

    /**
     * The [Interactor] of the icon.
     */
    val icon: IconInteractor,

    /**
     * The [Interactor] of the text.
     */
    val text: TextInteractor,
) : Interactor(modifier)