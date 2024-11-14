package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.runtime.Composable

class AsyncImageStateProjector(
    private val interactor: AsyncImageStateInteractor,
    presenter: AsyncImagePresenter = AsyncImagePresenter.Default
) : AsyncImageProjector(interactor, presenter) {
    @Composable
    override fun getResult(): AsyncImageResult = interactor.state
}