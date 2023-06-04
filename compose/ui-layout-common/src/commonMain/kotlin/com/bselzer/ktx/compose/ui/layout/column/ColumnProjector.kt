package com.bselzer.ktx.compose.ui.layout.column

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation
import com.bselzer.ktx.compose.ui.layout.LocalLayoutOrientation
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
    interactor: ColumnInteractor = ColumnInteractor.Default,
    presenter: ColumnPresenter = ColumnPresenter.Default
) : Projector<ColumnInteractor, ColumnPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        vararg content: @Composable ColumnScope.() -> Unit,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Column(
            modifier = combinedModifier,
            verticalArrangement = presenter.verticalArrangement,
            horizontalAlignment = presenter.horizontalAlignment,
        ) {
            CompositionLocalProvider(
                LocalLayoutOrientation provides LayoutOrientation.VERTICAL
            ) {
                val dividerProjector = interactor.divider?.let { divider -> DividerProjector(divider, presenter.divider) }
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
 *
 * By default, the color is transparent to act like a Spacer.
 */
@Composable
fun spacedColumnProjector(
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