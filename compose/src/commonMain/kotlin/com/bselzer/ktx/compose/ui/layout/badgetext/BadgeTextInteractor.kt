package com.bselzer.ktx.compose.ui.layout.badgetext

import com.bselzer.ktx.compose.ui.layout.badge.BadgeInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class BadgeTextInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,
    val badge: BadgeInteractor = BadgeInteractor.Default,
    val text: TextInteractor
) : Interactor(modifiers)