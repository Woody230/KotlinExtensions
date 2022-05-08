package com.bselzer.ktx.compose.ui.layout.description

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class DescriptionProjection(
    override val logic: DescriptionLogic,
    override val presentation: DescriptionPresentation = DescriptionPresentation.Default
) : Projector<DescriptionLogic, DescriptionPresentation>() {
    private val titleProjection = TextProjection(logic.title, presentation.title)
    private val subtitleProjection = TextProjection(logic.subtitle, presentation.subtitle)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = Column {
        titleProjection.project()
        subtitleProjection.project()
    }
}