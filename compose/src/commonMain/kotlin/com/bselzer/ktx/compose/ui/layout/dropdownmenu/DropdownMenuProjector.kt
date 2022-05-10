package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DropdownMenuProjector(
    override val interactor: DropdownMenuInteractor,
    override val presenter: DropdownMenuPresenter
) : Projector<DropdownMenuInteractor, DropdownMenuPresenter>() {
    private val iconProjections = interactor.icons.map { interaction -> IconButtonProjector(interaction, presenter.icon) }

    @Composable
    fun project(
        modifier: Modifier = Modifier,
    ) = contextualize {
        DropdownMenu(
            expanded = interactor.expanded,
            onDismissRequest = interactor.onDismissRequest,
            focusable = interactor.focusable,
            modifier = modifier,
            offset = offset,
        ) {
            iconProjections.forEach { icon -> icon.project() }
        }
    }
}