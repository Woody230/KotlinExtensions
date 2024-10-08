package com.bselzer.ktx.compose.ui.layout.alertdialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.AlertDialog
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
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
    ) = Projection(modifier = modifier, constrained = false, content = content)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        constrained: Boolean,
        content: @Composable () -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val negativeProjector = interactor.negativeButton?.let { TextButtonProjector(it, presenter.button) }
        val neutralProjector = interactor.neutralButton?.let { TextButtonProjector(it, presenter.button) }
        val positiveProjector = interactor.positiveButton?.let { TextButtonProjector(it, presenter.button) }
        val titleProjector = interactor.title?.let { TextProjector(it, presenter.title) }

        // TODO animation?
        if (interactor.state == DialogState.OPENED) {
            if (!constrained) {
                AlertDialog(
                    onDismissRequest = interactor.onDismissRequest,
                    buttons = { ProjectButtons(negativeProjector, neutralProjector, positiveProjector) },
                    modifier = combinedModifier,
                    title = titleProjector?.let { title -> @Composable { title.Projection() } },
                    text = content,
                    shape = presenter.shape,
                    backgroundColor = presenter.backgroundColor,
                    contentColor = presenter.contentColor
                )
            } else {
                Dialog(
                    onDismissRequest = interactor.onDismissRequest,
                ) {
                    Surface(
                        modifier = combinedModifier,
                        shape = presenter.shape,
                        color = presenter.backgroundColor,
                        contentColor = presenter.contentColor,
                    ) {
                        ConstraintContent(
                            // TODO can't properly wrap content without title/buttons going out of view unless the size is defined (through the filling)
                            modifier = Modifier.fillMaxHeight(),
                            negativeProjector = negativeProjector,
                            neutralProjector = neutralProjector,
                            positiveProjector = positiveProjector,
                            titleProjector = titleProjector,
                            content = content
                        )
                    }
                }
            }
        }
    }


    @Composable
    private fun ConstraintContent(
        modifier: Modifier,
        negativeProjector: TextButtonProjector?,
        neutralProjector: TextButtonProjector?,
        positiveProjector: TextButtonProjector?,
        titleProjector: TextProjector?,
        content: @Composable () -> Unit
    ) = ConstraintLayout(modifier = modifier) {
        val (titleBox, contentBox, buttonBox) = createRefs()

        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .defaultMinSize(minHeight = if (titleProjector == null) 0.dp else 64.dp)
                .constrainAs(titleBox) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, 24.dp)
                    end.linkTo(parent.end, 8.dp)
                    width = Dimension.fillToConstraints
                }
        ) {
            titleProjector?.Projection()
        }

        Box(
            modifier = Modifier.constrainAs(contentBox) {
                top.linkTo(titleBox.bottom)
                bottom.linkTo(buttonBox.top)
                start.linkTo(parent.start, 24.dp)
                end.linkTo(parent.end, 8.dp)
                width = Dimension.fillToConstraints
                height = Dimension.preferredWrapContent
            }
        ) {
            content()
        }

        Box(
            modifier = Modifier.constrainAs(buttonBox) {
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
            }
        ) {
            ProjectButtons(negativeProjector, neutralProjector, positiveProjector)
        }
    }

    @Composable
    private fun ProjectButtons(
        negativeProjector: TextButtonProjector?,
        neutralProjector: TextButtonProjector?,
        positiveProjector: TextButtonProjector?
    ) = ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 8.dp, top = 8.dp, bottom = 8.dp)
    ) {
        val (neutral, core) = createRefs()

        Row(modifier = Modifier.constrainAs(neutral) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(core.start)
            width = Dimension.fillToConstraints
        }) {
            neutralProjector?.let { neutralButton ->
                neutralButton.Projection()

                if (positiveProjector != null || negativeProjector != null) {
                    Spacer(width = 8.dp)
                }
            }
        }

        Row(modifier = Modifier.constrainAs(core) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end)
        }) {
            negativeProjector?.let { negativeButton ->
                negativeButton.Projection()

                if (positiveProjector != null) {
                    Spacer(width = 8.dp)
                }
            }

            positiveProjector?.Projection()
        }
    }
}