package com.bselzer.ktx.compose.ui.layout.badge

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.Badge
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class BadgeProjection(
    override val logic: BadgeLogic = BadgeLogic(),
    override val presentation: BadgePresentation = BadgePresentation()
) : Projectable<BadgeLogic, BadgePresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable (RowScope.() -> Unit)? = null
    ) {
        val backgroundColor = ComposeMerger.color.resolve(presentation.backgroundColor, MaterialTheme.colors.error)
        val contentColor = ComposeMerger.color.resolve(presentation.contentColor, contentColorFor(backgroundColor))
        Badge(
            modifier = modifier,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            content = content
        )
    }
}