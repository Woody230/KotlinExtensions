package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class RowProjector(
    override val interactor: RowInteractor = RowInteractor.Default,
    override val presenter: RowPresenter = RowPresenter.Default
) : Projector<RowInteractor, RowPresenter>() {
    private val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        vararg content: @Composable RowScope.() -> Unit,
    ) = contextualize(modifier) { combinedModifier ->
        Row(
            modifier = combinedModifier,
            horizontalArrangement = horizontalArrangement,
            verticalAlignment = verticalAlignment,
        ) {
            if (prepend.toBoolean()) {
                dividerProjector?.project()
            }

            content.forEachIndexed { index, function ->
                function()

                if (index != content.lastIndex) {
                    dividerProjector?.project()
                }
            }

            if (append.toBoolean()) {
                dividerProjector?.project()
            }
        }
    }
}