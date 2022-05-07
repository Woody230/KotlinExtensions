package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresentation
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonPresentation

data class AlertDialogPresentation(
    /**
     * The [PresentationModel] for the negative action button for dismissal.
     */
    val negativeButton: TextButtonPresentation = TextButtonPresentation.Default,

    /**
     * The [PresentationModel] for the neutral action button for an alternative action.
     */
    val neutralButton: TextButtonPresentation = TextButtonPresentation.Default,

    /**
     * The [PresentationModel] for the positive action button for confirmation.
     */
    val positiveButton: TextButtonPresentation = TextButtonPresentation.Default,

    /**
     * The [PresentationModel] for the title.
     */
    val title: TextPresentation = TextPresentation.Default,

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
) : Presenter<AlertDialogPresentation>() {
    companion object {
        @Stable
        val Default = AlertDialogPresentation()
    }

    override fun safeMerge(other: AlertDialogPresentation) = AlertDialogPresentation(
        negativeButton = negativeButton.merge(other.negativeButton),
        neutralButton = neutralButton.merge(other.neutralButton),
        positiveButton = positiveButton.merge(other.positiveButton),
        title = title.merge(other.title),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized() = AlertDialogPresentation(
        negativeButton = negativeButton.localized(),
        neutralButton = neutralButton.localized(),
        positiveButton = positiveButton.localized(),
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
