package com.bselzer.ktx.library

import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.mikepenz.aboutlibraries.entity.Library

data class LibraryInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,
    val libraries: List<Library>
) : Interactor(modifiers)