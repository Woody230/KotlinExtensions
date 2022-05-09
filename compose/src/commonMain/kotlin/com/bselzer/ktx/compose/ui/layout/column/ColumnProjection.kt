package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjection
import com.bselzer.ktx.compose.ui.layout.modifier.then
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ColumnProjection(
    override val logic: ColumnLogic = ColumnLogic.Default,
    override val presentation: ColumnPresentation = ColumnPresentation.Default
) : Projector<ColumnLogic, ColumnPresentation>() {
    private val dividerProjection = logic.divider?.let { divider -> DividerProjection(divider, presentation.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        vararg content: @Composable ColumnScope.() -> Unit,
    ) = contextualize {
        Column(
            modifier = modifier.then(logic.modifiers),
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            if (prepend.toBoolean()) {
                dividerProjection?.project()
            }

            content.forEachIndexed { index, function ->
                function()

                if (index != content.lastIndex) {
                    dividerProjection?.project()
                }
            }

            if (append.toBoolean()) {
                dividerProjection?.project()
            }
        }
    }
}