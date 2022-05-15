package com.bselzer.ktx.compose.ui.layout.background.image

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.box.BoxPresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.MatchParent
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BackgroundImagePresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presenter] for the container holding the [background] image.
     */
    val container: BoxPresenter = BoxPresenter.Default,

    /**
     * The [Presenter] for the background image.
     */
    val background: ImagePresenter = ImagePresenter.Default,
) : Presenter<BackgroundImagePresenter>(modifier) {
    companion object {
        @Stable
        val Default = BackgroundImagePresenter()
    }

    override fun safeMerge(other: BackgroundImagePresenter) = BackgroundImagePresenter(
        modifier = modifier.merge(other.modifier),
        container = container.merge(other.container),
        background = background.merge(other.background),
    )

    @Composable
    override fun localized() = copy(
        container = container.localized(),
        background = background.localized(),
    )
}

/**
 * Creates an [ImagePresenter] for a background image that fills the container.
 */
@Composable
fun backgroundImagePresenter() = ImagePresenter(
    modifier = ModularSize.FillSize,
) merge baseBackgroundImagePresenter()

/**
 * Creates an [ImagePresenter] for a background image that matches the size of the parent.
 */
@Composable
fun BoxScope.backgroundImagePresenter() = ImagePresenter(
    // Need to use matchParentSize() so that the image does not participate in sizing and can just fill the resulting size.
    modifier = MatchParent(this),
) merge baseBackgroundImagePresenter()

@Composable
private fun baseBackgroundImagePresenter() = ImagePresenter(
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
)