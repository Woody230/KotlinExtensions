package com.bselzer.ktx.compose.ui.layout.drawer.header

import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.description.DescriptionInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class DrawerHeaderInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val container: ColumnInteractor = ColumnInteractor.Default,
    val image: ImageInteractor? = null,
    val description: DescriptionInteractor? = null,
) : Interactor(modifier)