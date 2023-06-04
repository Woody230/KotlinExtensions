package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.progress.indicator.CircularIndicatorPresenter
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class AsyncImagePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    val image: ImagePresenter = ImagePresenter.Default,
    val progress: CircularIndicatorPresenter = CircularIndicatorPresenter.Default
) : Presenter<AsyncImagePresenter>(modifier) {
    companion object {
        @Stable
        val Default = AsyncImagePresenter()
    }

    override fun safeMerge(other: AsyncImagePresenter) = AsyncImagePresenter(
        modifier = modifier.merge(other.modifier),
        image = image.merge(other.image),
        progress = progress.merge(other.progress)
    )

    @Composable
    override fun localized() = AsyncImagePresenter(
        // Use the size of the containing Box.
        image = ImagePresenter(modifier = ModularSize.FillSize),
        progress = CircularIndicatorPresenter(modifier = ModularSize.FillSize)
    ).merge(this)
}