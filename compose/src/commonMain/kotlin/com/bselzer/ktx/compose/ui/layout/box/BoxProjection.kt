package com.bselzer.ktx.compose.ui.layout.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class BoxProjection(
    override val logic: BoxLogic = BoxLogic(),
    override val presentation: BoxPresentation = BoxPresentation()
) : Projectable<BoxLogic, BoxPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable BoxScope.() -> Unit
    ) = Box(
        modifier = modifier,
        contentAlignment = presentation.contentAlignment,
        propagateMinConstraints = presentation.propagateMinConstraints,
        content = content
    )
}