package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjection
import com.bselzer.ktx.compose.ui.layout.modifier.then
import com.bselzer.ktx.compose.ui.layout.project.Projector

class RowProjection(
    override val logic: RowLogic = RowLogic.Default,
    override val presentation: RowPresentation = RowPresentation.Default
) : Projector<RowLogic, RowPresentation>() {
    private val dividerProjection = logic.divider?.let { divider -> DividerProjection(divider, presentation.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        vararg content: @Composable RowScope.() -> Unit,
    ) = contextualize {
        Row(
            modifier = modifier.then(logic.modifiers),
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
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