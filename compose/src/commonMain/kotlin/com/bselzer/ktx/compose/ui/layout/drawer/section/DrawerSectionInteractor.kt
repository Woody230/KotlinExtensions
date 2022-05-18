package com.bselzer.ktx.compose.ui.layout.drawer.section

import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.drawer.component.DrawerComponentInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class DrawerSectionInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the column holding the components.
     */
    val container: ColumnInteractor = ColumnInteractor.Default,

    val title: TextInteractor? = null,
    val components: List<DrawerComponentInteractor>,
) : Interactor(modifier)