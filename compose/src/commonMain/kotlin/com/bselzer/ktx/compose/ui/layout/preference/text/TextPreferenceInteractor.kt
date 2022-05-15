package com.bselzer.ktx.compose.ui.layout.preference.text

import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class TextPreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the image.
     */
    val image: ImageInteractor,

    /**
     * The [Interactor] for the title.
     */
    val title: TextInteractor,

    /**
     * The [Interactor] for the subtitle.
     */
    val subtitle: TextInteractor
) : Interactor(modifier)