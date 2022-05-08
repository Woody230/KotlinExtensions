package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DropdownMenuProjection(
    override val logic: DropdownMenuLogic,
    override val presentation: DropdownMenuPresentation
) : Projector<DropdownMenuLogic, DropdownMenuPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        content: @Composable ColumnScope.() -> Unit
    ) = contextualize {
        DropdownMenu(
            expanded = logic.expanded,
            onDismissRequest = logic.onDismissRequest,
            focusable = logic.focusable,
            modifier = modifier,
            offset = offset,
            content = content
        )
    }
}