package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjection
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DropdownMenuProjection(
    override val logic: DropdownMenuLogic,
    override val presentation: DropdownMenuPresentation
) : Projector<DropdownMenuLogic, DropdownMenuPresentation>() {
    private val iconProjections = logic.icons.map { logic -> IconButtonProjection(logic, presentation.icon) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize {
        DropdownMenu(
            expanded = logic.expanded,
            onDismissRequest = logic.onDismissRequest,
            focusable = logic.focusable,
            modifier = modifier,
            offset = offset,
        ) {
            iconProjections.forEach { icon -> icon.project() }
        }
    }
}