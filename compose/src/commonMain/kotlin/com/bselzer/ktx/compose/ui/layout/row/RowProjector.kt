package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.divider.DividerInteractor
import com.bselzer.ktx.compose.ui.layout.divider.DividerPresenter
import com.bselzer.ktx.compose.ui.layout.divider.DividerProjector
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.function.objects.safeMerge

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

/**
 * Creates a [RowProjector] with a divider with the given [color], [thickness], [interactor], and [presenter].
 */
@Composable
fun dividedRowProjector(
    color: Color = Color.Transparent,
    thickness: Dp = ComposeMerger.dp.default,
    interactor: RowInteractor = RowInteractor.Default,
    presenter: RowPresenter = RowPresenter.Default
) = RowProjector(
    interactor = interactor.copy(divider = DividerInteractor.Default.safeMerge(interactor.divider, null)),
    presenter = RowPresenter(
        divider = DividerPresenter(
            color = color,
            thickness = thickness
        )
    ).merge(presenter)
)