package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.modifier.then
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ColumnProjector(
    override val interactor: ColumnInteractor = ColumnInteractor.Default,
    override val presenter: ColumnPresenter = ColumnPresenter.Default
) : Projector<ColumnInteractor, ColumnPresenter>() {
    private val dividerProjection = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        vararg content: @Composable ColumnScope.() -> Unit,
    ) = contextualize {
        Column(
            modifier = modifier.then(interactor.modifiers),
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