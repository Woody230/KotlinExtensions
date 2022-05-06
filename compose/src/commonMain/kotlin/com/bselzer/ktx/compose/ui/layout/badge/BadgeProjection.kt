package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BadgeProjection(
    override val logic: BadgeLogic = BadgeLogic.Default,
    override val presentation: BadgePresentation = BadgePresentation.Default
) : Projector<BadgeLogic, BadgePresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable (RowScope.() -> Unit)? = null
    ) = contextualize {
        Badge(
            modifier = modifier,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}