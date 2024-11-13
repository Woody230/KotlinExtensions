package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

abstract class AsyncImageProjector(
    interactor: AsyncImageInteractor,
    presenter: AsyncImagePresenter
) : Projector<AsyncImageInteractor, AsyncImagePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Box(
            modifier = combinedModifier,
        ) {
            when (val result = getResult()) {
                is AsyncImageResult.Loading -> {
                    LoadingImage(interactor, presenter)
                }
                is AsyncImageResult.Failed -> {
                    FailedImage(interactor, presenter)
                }
                is AsyncImageResult.Success -> {
                    result.SuccessImage(interactor, presenter)
                }
            }
        }
    }

    @Composable
    protected abstract fun getResult(): AsyncImageResult

    @Composable
    private fun LoadingImage(interactor: AsyncImageInteractor, presenter: AsyncImagePresenter) {
        val loadingImage = interactor.loadingImage
        if (loadingImage != null) {
            ImageProjector(
                interactor = loadingImage,
                presenter = presenter.image
            ).Projection()
            return
        }

        val loadingProgress = interactor.loadingProgress
        if (loadingProgress != null) {
            ProgressIndicatorProjector(
                interactor = loadingProgress,
                presenter = presenter.progress
            ).Projection()
        }
    }

    @Composable
    private fun FailedImage(interactor: AsyncImageInteractor, presenter: AsyncImagePresenter) {
        // Default to nothing if an image is not provided.
        interactor.failedImage?.let { failedImage ->
            ImageProjector(
                interactor = failedImage,
                presenter = presenter.image
            ).Projection()
        }
    }

    @Composable
    private fun AsyncImageResult.Success.SuccessImage(interactor: AsyncImageInteractor, presenter: AsyncImagePresenter) = ImageProjector(
        interactor = ImageInteractor(
            painter = painter,
            contentDescription = interactor.contentDescription
        ),
        presenter = presenter.image
    ).Projection()
}