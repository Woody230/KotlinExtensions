package com.bselzer.ktx.compose.ui.layout.backgroundcolumn

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.box.BoxPresenter
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.MatchParent
import com.bselzer.ktx.compose.ui.layout.modifier.ModularSize
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class BackgroundColumnPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presenter] for the container holding the [background] image and column.
     */
    val container: BoxPresenter = BoxPresenter.Default,

    /**
     * The [Presenter] for the background image.
     */
    val background: ImagePresenter = ImagePresenter.Default,

    val column: ColumnPresenter = ColumnPresenter.Default
) : Presenter<BackgroundColumnPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = BackgroundColumnPresenter()
    }

    override fun safeMerge(other: BackgroundColumnPresenter) = BackgroundColumnPresenter(
        modifiers = modifiers.merge(other.modifiers),
        container = container.merge(other.container),
        background = background.merge(other.background),
        column = column.merge(other.column)
    )

    @Composable
    override fun localized() = copy(
        container = container.localized(),
        background = background.localized(),
        column = column.localized()
    )
}

@Composable
fun backgroundImagePresenter() = ImagePresenter(
    modifiers = PresentableModifiers(size = ModularSize.FillSize),
) merge baseBackgroundImagePresenter()

@Composable
fun BoxScope.backgroundImagePresenter() = ImagePresenter(
    // Need to use matchParentSize() so that the image does not participate in sizing and can just fill the resulting size.
    modifiers = PresentableModifiers(size = MatchParent(this)),
) merge baseBackgroundImagePresenter()

@Composable
private fun baseBackgroundImagePresenter() = ImagePresenter(
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
)