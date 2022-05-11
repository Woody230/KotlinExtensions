package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.toTriState
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ColumnProjector(
    override val interactor: ColumnInteractor = ColumnInteractor.Default,
    override val presenter: ColumnPresenter = ColumnPresenter.Default
) : Projector<ColumnInteractor, ColumnPresenter>() {
    private val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        vararg content: @Composable ColumnScope.() -> Unit,
    ) = contextualize(modifier) { combinedModifier ->
        Column(
            modifier = combinedModifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
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

/**
 * Creates a [ColumnProjector] with a divider with the given [color], [thickness], and [interactor].
 *
 * If [prepend] is true then a divider will appear before the items.
 *
 * If [append] is true then a divider will appear after the items.
 */
@Composable
fun dividedColumnProjector(
    color: Color = Color.Transparent,
    thickness: Dp = ComposeMerger.dp.default,
    interactor: DividerInteractor = DividerInteractor.Default,
    prepend: Boolean = false,
    append: Boolean = false
) = ColumnProjector(
    interactor = ColumnInteractor(
        divider = interactor
    ),
    presenter = ColumnPresenter(
        prepend = prepend.toTriState(),
        append = append.toTriState(),
        divider = DividerPresenter(
            color = color,
            thickness = thickness
        )
    )
)