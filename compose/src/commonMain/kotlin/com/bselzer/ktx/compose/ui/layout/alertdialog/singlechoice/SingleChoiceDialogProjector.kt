package com.bselzer.ktx.compose.ui.layout.alertdialog.singlechoice

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.radiobutton.RadioButtonInteractor
import com.bselzer.ktx.compose.ui.layout.radiobutton.RadioButtonProjector
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class SingleChoiceDialogProjector<Choice>(
    override val interactor: SingleChoiceDialogInteractor<Choice>,
    override val presenter: SingleChoiceDialogPresenter = SingleChoiceDialogPresenter.Default
) : Projector<SingleChoiceDialogInteractor<Choice>, SingleChoiceDialogPresenter>() {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier ->
        LazyColumn(modifier = combinedModifier) {
            items(interactor.values) { choice ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RadioButtonProjector(
                        presenter = radioButton,
                        interactor = RadioButtonInteractor(
                            selected = choice == interactor.selected,
                            onClick = { interactor.onSelection(choice) }
                        ),
                    ).Projection()

                    Spacer(modifier = Modifier.width(24.dp))

                    TextProjector(
                        presenter = text,
                        interactor = TextInteractor(text = interactor.getLabel(choice))
                    ).Projection()
                }
            }
        }
    }
}