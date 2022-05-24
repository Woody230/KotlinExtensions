package com.bselzer.ktx.compose.image.ui.layout.async

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import com.bselzer.ktx.compose.image.ui.layout.asImageBitmap
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.progress.indicator.ProgressIndicatorProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class AsyncImageProjector(
    interactor: AsyncImageInteractor,
    presenter: AsyncImagePresenter = AsyncImagePresenter.Default
) : Projector<AsyncImageInteractor, AsyncImagePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Box(
            modifier = combinedModifier,
        ) {
            when (val result = produceResult(interactor).value) {
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
    private fun produceResult(interactor: AsyncImageInteractor): State<AsyncImageResult> = run {
        val url = interactor.url
        produceState<AsyncImageResult>(initialValue = AsyncImageResult.Loading, key1 = url) {
            value = run {
                val image = interactor.getImage(url)

                // Ignore empty bytes which will cause exceptions when trying to create a bitmap.
                val content = if (image?.content?.isNotEmpty() == true) image.content else null
                val bitmap = content?.let {
                    try {
                        // TODO transformations
                        content.asImageBitmap()
                    } catch (ex: Exception) {
                        null
                    }
                }

                val painter = bitmap?.let {
                    BitmapPainter(
                        image = bitmap,
                        srcOffset = interactor.srcOffset,
                        filterQuality = interactor.filterQuality
                    )
                }

                if (painter == null) {
                    AsyncImageResult.Failed
                } else {
                    AsyncImageResult.Success(painter)
                }
            }
        }
    }

    @Composable
    private fun LoadingImage(interactor: AsyncImageInteractor, presenter: AsyncImagePresenter) {
        // Default to a progress bar if an image is not provided.
        if (interactor.loadingImage == null) {
            ProgressIndicatorProjector(
                presenter = presenter.progress
            ).Projection()
        } else {
            ImageProjector(
                interactor = interactor.loadingImage,
                presenter = presenter.image
            ).Projection()
        }
    }

    @Composable
    private fun FailedImage(interactor: AsyncImageInteractor, presenter: AsyncImagePresenter) {
        // Default to nothing if an image is not provided.
        if (interactor.failedImage != null) {
            ImageProjector(
                interactor = interactor.failedImage,
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