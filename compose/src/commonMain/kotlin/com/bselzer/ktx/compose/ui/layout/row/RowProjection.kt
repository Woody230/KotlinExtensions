package com.bselzer.ktx.compose.ui.layout.row

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class RowProjection(
    override val logic: RowLogic = RowLogic(),
    override val presentation: RowPresentation = RowPresentation()
) : Projectable<RowLogic, RowPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable RowScope.() -> Unit,
    ) = Row(
        modifier = modifier,
        horizontalArrangement = presentation.horizontalArrangement,
        verticalAlignment = presentation.verticalAlignment,
        content = content
    )
}