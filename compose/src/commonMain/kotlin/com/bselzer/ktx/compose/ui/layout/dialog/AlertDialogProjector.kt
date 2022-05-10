package com.bselzer.ktx.compose.ui.layout.dialog

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

abstract class AlertDialogProjector(
    final override val interactor: AlertDialogInteractor,
    final override val presenter: AlertDialogPresenter = AlertDialogPresenter.Default
) : Projector<AlertDialogInteractor, AlertDialogPresenter>() {
    private val negativeProjector = interactor.negativeButton?.let { negativeButton -> TextButtonProjector(negativeButton, presenter.button) }
    private val neutralProjector = interactor.neutralButton?.let { neutralButton -> TextButtonProjector(neutralButton, presenter.button) }
    private val positiveProjector = interactor.positiveButton?.let { positiveButton -> TextButtonProjector(positiveButton, presenter.button) }
    private val titleProjector = interactor.title?.let { title -> TextProjector(title, presenter.title) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        AlertDialog(
            onDismissRequest = interactor.onDismissRequest,
            buttons = { projectButtons() },
            modifier = combinedModifier,
            title = titleProjector?.let { title -> @Composable { title.project() } },
            text = content,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }

    @Composable
    private fun projectButtons() = ConstraintLayout(
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
            neutralProjector?.project()
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
                negativeButton.project()
                Spacer(width = 8.dp)
            }

            positiveProjector?.project()
        }
    }
}