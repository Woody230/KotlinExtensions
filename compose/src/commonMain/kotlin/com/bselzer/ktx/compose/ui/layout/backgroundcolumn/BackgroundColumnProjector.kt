package com.bselzer.ktx.compose.ui.layout.backgroundcolumn

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.box.BoxProjector
import com.bselzer.ktx.compose.ui.layout.column.ColumnProjector
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BackgroundColumnProjector(
    override val interactor: BackgroundColumnInteractor,
    override val presenter: BackgroundColumnPresenter = BackgroundColumnPresenter.Default
) : Projector<BackgroundColumnInteractor, BackgroundColumnPresenter>() {
    private val containerProjector = BoxProjector(interactor.container, presenter.container)
    private val columnProjector = ColumnProjector(interactor.column, presenter.column)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        containerProjector.Projection(
            modifier = combinedModifier
        ) {
            ImageProjector(
                interactor = interactor.background,
                presenter = backgroundImagePresenter() merge presenter.background
            ).Projection()

            columnProjector.Projection(modifier = Modifier, content)
        }
    }
}