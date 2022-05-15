package com.bselzer.ktx.compose.ui.layout.background.column

import com.bselzer.ktx.compose.ui.layout.box.BoxInteractor
import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class BackgroundColumnInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the container holding the [background] image and [column].
     */
    val container: BoxInteractor = BoxInteractor.Default,

    /**
     * The [Interactor] for the background image.
     */
    val background: ImageInteractor,

    val column: ColumnInteractor = ColumnInteractor.Default
) : Interactor(modifier)