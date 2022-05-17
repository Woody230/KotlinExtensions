package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.spacer.Spacer
import com.bselzer.ktx.compose.ui.layout.text.TextProjector
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonProjector

class AlertDialogProjector(
    interactor: AlertDialogInteractor,
    presenter: AlertDialogPresenter = AlertDialogPresenter.Default
) : Projector<AlertDialogInteractor, AlertDialogPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val negativeProjector = interactor.negativeButton?.let { TextButtonProjector(it, presenter.button) }
        val neutralProjector = interactor.neutralButton?.let { TextButtonProjector(it, presenter.button) }
        val positiveProjector = interactor.positiveButton?.let { TextButtonProjector(it, presenter.button) }
        val titleProjector = interactor.title?.let { TextProjector(it, presenter.title) }
        AlertDialog(
            onDismissRequest = interactor.onDismissRequest,
            buttons = { projectButtons(negativeProjector, neutralProjector, positiveProjector) },
            modifier = combinedModifier,
            title = titleProjector?.let { title -> @Composable { title.Projection() } },
            text = content,
            shape = presenter.shape,
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor
        )
    }

    @Composable
    private fun projectButtons(
        negativeProjector: TextButtonProjector?,
        neutralProjector: TextButtonProjector?,
        positiveProjector: TextButtonProjector?
    ) = ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        val (neutral, spacer, core) = createRefs()

        Row(modifier = Modifier.constrainAs(neutral) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(spacer.start)
        }) {
            neutralProjector?.Projection()
        }

        Spacer(modifier = Modifier.constrainAs(spacer) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(neutral.end)
            end.linkTo(core.start)
            width = Dimension.fillToConstraints
        })

        Row(modifier = Modifier.constrainAs(core) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(spacer.end)
            end.linkTo(parent.end)
        }) {
            negativeProjector?.let { negativeButton ->
                negativeButton.Projection()
                Spacer(width = 8.dp)
            }

            positiveProjector?.Projection()
        }
    }
}