package com.bselzer.ktx.compose.ui.layout.dropdownmenu

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DropdownMenuProjector(
    override val interactor: DropdownMenuInteractor,
    override val presenter: DropdownMenuPresenter
) : Projector<DropdownMenuInteractor, DropdownMenuPresenter>() {
    private val iconProjectors = interactor.icons.map { interaction -> IconButtonProjector(interaction, presenter.icon) }

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier ->
        DropdownMenu(
            expanded = interactor.expanded,
            onDismissRequest = interactor.onDismissRequest,
            focusable = interactor.focusable,
            modifier = combinedModifier,
            offset = offset,
        ) {
            iconProjectors.forEach { icon -> icon.Projection() }
        }
    }
}