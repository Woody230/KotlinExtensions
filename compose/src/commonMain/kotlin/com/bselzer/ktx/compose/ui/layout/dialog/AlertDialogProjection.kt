package com.bselzer.ktx.compose.ui.layout.dialog

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjection
import com.bselzer.ktx.compose.ui.layout.textbutton.TextButtonProjection

abstract class AlertDialogProjection(
    final override val logic: AlertDialogLogic,
    final override val presentation: AlertDialogPresentation = AlertDialogPresentation.Default
) : Projector<AlertDialogLogic, AlertDialogPresentation>() {
    private val negativeButton = logic.negativeButton?.let { negativeButton -> TextButtonProjection(negativeButton, presentation.negativeButton) }
    private val neutralButton = logic.neutralButton?.let { neutralButton -> TextButtonProjection(neutralButton, presentation.neutralButton) }
    private val positiveButton = logic.positiveButton?.let { positiveButton -> TextButtonProjection(positiveButton, presentation.positiveButton) }
    private val title = logic.title?.let { title -> TextProjection(title, presentation.title) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) = contextualize {
        project(
            onDismissRequest = logic.onDismissRequest,
            buttons = { projectButtons() },
            modifier = modifier,
            title = this@AlertDialogProjection.title?.let { title -> @Composable { title.project() } },
            text = content,
            shape = shape,
            backgroundColor = backgroundColor,
            contentColor = contentColor
        )
    }

    // TODO remove when AlertDialog is able to accessed in common https://github.com/JetBrains/compose-jb/issues/762
    @Composable
    protected abstract fun project(
        onDismissRequest: () -> Unit,
        buttons: @Composable () -> Unit,
        modifier: Modifier,
        title: (@Composable () -> Unit)?,
        text: @Composable (() -> Unit)?,
        shape: Shape,
        backgroundColor: Color,
        contentColor: Color,
    )

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
            neutralButton?.project()
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
            negativeButton?.let { negativeButton ->
                negativeButton.project()
                Spacer(width = 8.dp)
            }

            positiveButton?.project()
        }
    }
}