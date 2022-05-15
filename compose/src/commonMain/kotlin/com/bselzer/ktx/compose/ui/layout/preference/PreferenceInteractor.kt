package com.bselzer.ktx.compose.ui.layout.preference

import com.bselzer.ktx.compose.ui.layout.description.DescriptionInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor

data class PreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,

    /**
     * The [Interactor] for the image.
     */
    val image: ImageInteractor,

    /**
     * The [Interactor] for the name and description of the preference.
     */
    val description: DescriptionInteractor,
) : Interactor(modifier)