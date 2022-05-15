package com.bselzer.ktx.compose.ui.layout.background.column

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.box.BoxPresenter
import com.bselzer.ktx.compose.ui.layout.column.ColumnPresenter
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
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