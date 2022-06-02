package com.bselzer.ktx.compose.ui.layout.description

import com.bselzer.ktx.compose.ui.layout.column.ColumnInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.textInteractor

data class DescriptionInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the container holding the title and subtitle.
     */
    val container: ColumnInteractor = ColumnInteractor.Default,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor,

    /**
     * The [Interactor] for the subtitle.
     */
    val subtitle: TextInteractor? = null
) : Interactor(modifier) {
    constructor(title: String, subtitle: String?) : this(title = title.textInteractor(), subtitle = subtitle?.textInteractor())
}