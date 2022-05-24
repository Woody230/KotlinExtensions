package com.bselzer.ktx.compose.ui.layout.progress.indicator

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class ProgressIndicatorProjector<Presenter>(
    interactor: ProgressIndicatorInteractor = ProgressIndicatorInteractor.Default,
    presenter: Presenter
) : Projector<ProgressIndicatorInteractor, Presenter>(
    interactor, presenter
) where Presenter : ProgressIndicatorPresenter<Presenter> {
    companion object {
        @Stable
        val IndefiniteCircular = ProgressIndicatorProjector(
            presenter = CircularIndicatorPresenter.Default
        )

        @Stable
        val IndefiniteLinear = ProgressIndicatorProjector(
            presenter = LinearIndicatorPresenter.Default
        )
    }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        if (interactor.progress == null) {
            // Display the indefinite progress indicator.
            when (presenter) {
                is CircularIndicatorPresenter -> CircularProgressIndicator(
                    modifier = combinedModifier,
                    color = presenter.color,
                    strokeWidth = presenter.strokeWidth
                )
                is LinearIndicatorPresenter -> LinearProgressIndicator(
                    modifier = combinedModifier,
                    color = presenter.color,
                    backgroundColor = presenter.backgroundColor
                )
            }
        } else {
            // Display the progress indicator with explcitly defined progress.
            when (presenter) {
                is CircularIndicatorPresenter -> CircularProgressIndicator(
                    progress = interactor.progress,
                    modifier = combinedModifier,
                    color = presenter.color,
                    strokeWidth = presenter.strokeWidth
                )
                is LinearIndicatorPresenter -> LinearProgressIndicator(
                    progress = interactor.progress,
                    modifier = combinedModifier,
                    color = presenter.color,
                    backgroundColor = presenter.backgroundColor
                )
            }
        }
    }
}