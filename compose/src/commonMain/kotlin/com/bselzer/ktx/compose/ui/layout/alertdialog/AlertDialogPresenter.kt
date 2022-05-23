package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonPresenter

data class AlertDialogPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

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
) : Presenter<AlertDialogPresenter>(modifier) {
    companion object {
        @Stable
        val Default = AlertDialogPresenter()
    }

    override fun safeMerge(other: AlertDialogPresenter) = AlertDialogPresenter(
        modifier = modifier.merge(other.modifier),
        button = button.merge(other.button),
        title = title.merge(other.title),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized() = AlertDialogPresenter(
        title = TextPresenter(
            // Implementation defaults to subtitle1 but material2 guidelines specify h6 and bold
            fontWeight = FontWeight.Bold,
            textStyle = MaterialTheme.typography.h6
        ),
        button = TextButtonPresenter(
            // Implementation does not provide a default for these
            text = TextPresenter(
                fontWeight = FontWeight.SemiBold,
                textStyle = MaterialTheme.typography.button
            )
        ),
        shape = MaterialTheme.shapes.medium,
        backgroundColor = MaterialTheme.colors.surface
    ).merge(this).run {
        copy(
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}
