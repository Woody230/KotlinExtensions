package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class RowProjection(
    override val logic: RowLogic = RowLogic(),
    override val presentation: RowPresentation = RowPresentation()
) : Projector<RowLogic, RowPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable RowScope.() -> Unit,
    ) = contextualize {
        Row(
            modifier = modifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
            content = content
        )
    }
}