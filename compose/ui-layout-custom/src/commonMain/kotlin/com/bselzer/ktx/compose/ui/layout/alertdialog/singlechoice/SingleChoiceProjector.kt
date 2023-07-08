package com.bselzer.ktx.compose.ui.layout.alertdialog.singlechoice

import androidx.compose.foundation.clickable
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

class SingleChoiceProjector<Choice>(
    interactor: SingleChoiceInteractor<Choice>,
    presenter: SingleChoicePresenter = SingleChoicePresenter.Default
) : Projector<SingleChoiceInteractor<Choice>, SingleChoicePresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        LazyColumn(modifier = combinedModifier) {
            items(interactor.values) { choice ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().clickable {
                        // Allow the whole row to be clickable instead of just the radio button.
                        interactor.onSelection(choice)
                    }
                ) {
                    RadioButtonProjector(
                        presenter = presenter.radioButton,
                        interactor = RadioButtonInteractor(
                            selected = choice == interactor.selected,

                            // Keep the radio button onClick so that minimum touch size is applied.
                            onClick = { interactor.onSelection(choice) }
                        ),
                    ).Projection()

                    Spacer(modifier = Modifier.width(24.dp))

                    TextProjector(
                        presenter = presenter.text,
                        interactor = TextInteractor(text = interactor.getLabel(choice))
                    ).Projection()
                }
            }
        }
    }
}