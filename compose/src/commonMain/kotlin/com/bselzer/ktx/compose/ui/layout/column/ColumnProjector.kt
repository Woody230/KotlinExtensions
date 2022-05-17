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
import com.bselzer.ktx.compose.ui.layout.project.Interactable
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Projectable
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.function.objects.safeMerge

class ColumnProjector(
    override val interactor: ColumnInteractor = ColumnInteractor.Default,
    override val presenter: ColumnPresenter = ColumnPresenter.Default
) : Projector<ColumnInteractor, ColumnPresenter>() {
    private val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        vararg content: @Composable ColumnScope.() -> Unit,
    ) = contextualize(modifier) { combinedModifier ->
        Column(
            modifier = combinedModifier,
            verticalArrangement = verticalArrangement,
            horizontalAlignment = horizontalAlignment,
        ) {
            if (prepend.toBoolean()) {
                dividerProjector?.Projection()
            }

            content.forEachIndexed { index, function ->
                function()

                if (index != content.lastIndex) {
                    dividerProjector?.Projection()
                }
            }

            if (append.toBoolean()) {
                dividerProjector?.Projection()
            }
        }
    }
}

/**
 * Adds a [ColumnScope]d composable to the list if the associated projector is not null.
 *
 * @return true if a composable delegate is added
 */
fun <Interactor, Presenter, Projector> MutableList<@Composable ColumnScope.() -> Unit>.addIfNotNull(
    projector: Projector?,
    block: @Composable ColumnScope.(Projector) -> Unit
): Boolean where Interactor : Interactable, Presenter : Presentable<Presenter>, Projector : Projectable<Interactor, Presenter> {
    return if (projector == null) {
        false
    } else {
        add { block(projector) }
    }
}


/**
 * Creates a [ColumnProjector] with a divider with the given [color], [thickness], [interactor], and [presenter].
 */
@Composable
fun dividedColumnProjector(
    color: Color = Color.Transparent,
    thickness: Dp = ComposeMerger.dp.default,
    interactor: ColumnInteractor = ColumnInteractor.Default,
    presenter: ColumnPresenter = ColumnPresenter.Default
) = ColumnProjector(
    interactor = interactor.copy(divider = DividerInteractor.Default.safeMerge(interactor.divider, null)),
    presenter = ColumnPresenter(
        divider = DividerPresenter(
            color = color,
            thickness = thickness
        )
    ).merge(presenter)
)