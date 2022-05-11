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
    override val interactor: PreferenceSectionInteractor,
    override val presenter: PreferenceSectionPresenter = PreferenceSectionPresenter.Default
) : Projector<PreferenceSectionInteractor, PreferenceSectionPresenter>() {
    private val imageProjector = ImageProjector(interactor.image, presenter.image)
    private val titleProjector = TextProjector(interactor.title, presenter.title)

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) = contextualize(modifier) { combinedModifier ->
        Column(modifier = combinedModifier) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                imageProjector.Projection()
                Spacer(modifier = Modifier.width(PreferenceConstants.Spacing))
                titleProjector.Projection()
            }

            Spacer(modifier = Modifier.height(PreferenceConstants.SectionSpacing))

            content()
        }
    }
}

