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
import com.bselzer.ktx.compose.ui.layout.project.Interactable
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Projectable
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.function.objects.safeMerge

class RowProjector(
    interactor: RowInteractor = RowInteractor.Default,
    presenter: RowPresenter = RowPresenter.Default
) : Projector<RowInteractor, RowPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        vararg content: @Composable RowScope.() -> Unit,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Row(
            modifier = combinedModifier,
            horizontalArrangement = presenter.horizontalArrangement,
            verticalAlignment = presenter.verticalAlignment,
        ) {
            val dividerProjector = interactor.divider?.let { DividerProjector(it, presenter.divider) }
            if (presenter.prepend.toBoolean()) {
                dividerProjector?.Projection()
            }

            content.forEachIndexed { index, function ->
                function()

                if (index != content.lastIndex) {
                    dividerProjector?.Projection()
                }
            }

            if (presenter.append.toBoolean()) {
                dividerProjector?.Projection()
            }
        }
    }
}

/**
 * Adds a [RowScope]d composable to the list if the associated projector is not null.
 *
 * @return true if a composable delegate is added
 */
fun <Interactor, Presenter, Projector> MutableList<@Composable RowScope.() -> Unit>.addIfNotNull(
    projector: Projector?,
    block: @Composable RowScope.(Projector) -> Unit
): Boolean where Interactor : Interactable, Presenter : Presentable<Presenter>, Projector : Projectable<Interactor, Presenter> {
    return if (projector == null) {
        false
    } else {
        add { block(projector) }
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