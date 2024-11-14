package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AsyncPainterProjector(
    private val interactor: AsyncPainterInteractor,
    presenter: AsyncImagePresenter = AsyncImagePresenter.Default
) : AsyncImageProjector(interactor, presenter) {
    @Composable
    override fun getResult(): AsyncImageResult {
        return produceResult(interactor).value
    }

    @Composable
    private fun produceResult(interactor: AsyncPainterInteractor): State<AsyncImageResult> = run {
        val url = interactor.url
        produceState<AsyncImageResult>(initialValue = AsyncImageResult.Loading, key1 = url) {
            value = withContext(Dispatchers.Default) {
                val painter = interactor.getImage(url)
                if (painter == null) {
                    AsyncImageResult.Failed
                } else {
                    AsyncImageResult.Success(painter)
                }
            }
        }
    }
}