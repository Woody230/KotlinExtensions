package com.bselzer.ktx.compose.ui.layout.preference.section

import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class PreferenceSectionInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the image associated with the section content.
     */
    val image: ImageInteractor,

    /**
     * The [Interactor] for the title describing the section content.
     */
    val title: TextInteractor
) : Interactor(modifier)