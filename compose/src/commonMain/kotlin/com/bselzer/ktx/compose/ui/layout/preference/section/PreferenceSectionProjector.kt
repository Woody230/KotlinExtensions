package com.bselzer.ktx.compose.ui.layout.preference.section

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.preference.PreferenceConstants
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjector

class PreferenceSectionProjector(
    interactor: PreferenceSectionInteractor,
    presenter: PreferenceSectionPresenter = PreferenceSectionPresenter.Default
) : Projector<PreferenceSectionInteractor, PreferenceSectionPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        Column(modifier = combinedModifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val imageProjector = ImageProjector(interactor.image, presenter.image)
                val titleProjector = TextProjector(interactor.title, presenter.title)

                imageProjector.Projection()
                Spacer(modifier = Modifier.width(PreferenceConstants.Spacing))
                titleProjector.Projection()
            }

            Spacer(modifier = Modifier.height(PreferenceConstants.SectionSpacing))

            content()
        }
    }
}

