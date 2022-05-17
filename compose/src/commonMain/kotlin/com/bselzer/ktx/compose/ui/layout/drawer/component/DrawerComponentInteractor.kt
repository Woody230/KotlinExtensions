package com.bselzer.ktx.compose.ui.layout.drawer.component

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.row.RowInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class DrawerComponentInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val container: RowInteractor = RowInteractor.Default,
    val icon: IconInteractor? = null,
    val text: TextInteractor
) : Interactor(modifier)