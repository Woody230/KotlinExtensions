package com.bselzer.ktx.compose.aboutlibraries

import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.mikepenz.aboutlibraries.entity.Library

data class LibraryInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val libraries: List<Library>
) : Interactor(modifier)