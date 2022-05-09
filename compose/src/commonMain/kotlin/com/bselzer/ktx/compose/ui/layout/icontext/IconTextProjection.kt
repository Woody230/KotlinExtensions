package com.bselzer.ktx.compose.ui.layout.icontext

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.icon.IconProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.row.RowProjection
import com.bselzer.ktx.compose.ui.layout.text.TextProjection

class IconTextProjection(
    override val logic: IconTextLogic,
    override val presentation: IconTextPresentation = IconTextPresentation.Default
) : Projector<IconTextLogic, IconTextPresentation>() {
    private val containerProjection = RowProjection(logic.container, presentation.container)
    private val iconProjection = IconProjection(logic.icon, presentation.icon)
    private val textProjection = TextProjection(logic.text, presentation.text)

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = containerProjection.project(
        modifier = modifier,
        { iconProjection.project() },
        { textProjection.project(modifier = Modifier.weight(1f)) }
    )
}