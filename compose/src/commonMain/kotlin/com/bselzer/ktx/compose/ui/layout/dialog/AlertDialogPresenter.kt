package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonPresenter

data class AlertDialogPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * The [Presentable] for negative, neutral, and positive buttons.
     */
    val button: TextButtonPresenter = TextButtonPresenter.Default,

    /**
     * The [Presentable] for the title.
     */
    val title: TextPresenter = TextPresenter.Default,

    /**
     * The shape of the dialog.
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * The color of the dialog background.
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The color of the dialog content.
     */
    val contentColor: Color = ComposeMerger.color.default,
) : Presenter<AlertDialogPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = AlertDialogPresenter()
    }

    override fun safeMerge(other: AlertDialogPresenter) = AlertDialogPresenter(
        modifiers = modifiers.merge(other.modifiers),
        button = button.merge(other.button),
        title = title.merge(other.title),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized() = AlertDialogPresenter(
        button = button.localized(),
        title = title.localized(),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface
    ).merge(this).run {
        if (ComposeMerger.color.isDefault(contentColor)) {
            copy(contentColor = contentColorFor(backgroundColor))
        } else {
            this
        }
    }
}
