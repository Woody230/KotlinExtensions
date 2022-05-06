package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ColumnProjection(
    override val logic: ColumnLogic = ColumnLogic.Default,
    override val presentation: ColumnPresentation = ColumnPresentation.Default
) : Projector<ColumnLogic, ColumnPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit,
    ) = contextualize {
        Column(
            modifier = modifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
            content = content
        )
    }
}