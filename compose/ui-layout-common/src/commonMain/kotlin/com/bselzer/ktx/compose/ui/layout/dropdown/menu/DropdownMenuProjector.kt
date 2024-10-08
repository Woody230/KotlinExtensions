package com.bselzer.ktx.compose.ui.layout.dropdown.menu

import androidx.compose.material.DropdownMenu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.PopupProperties
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DropdownMenuProjector(
    interactor: DropdownMenuInteractor,
    presenter: DropdownMenuPresenter = DropdownMenuPresenter.Default
) : Projector<DropdownMenuInteractor, DropdownMenuPresenter>(interactor, presenter) {

    @Composable
    fun Projection(
        modifier: Modifier = Modifier,
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        val iconProjectors = interactor.icons.map { IconButtonProjector(it, presenter.icon) }

        DropdownMenu(
            expanded = interactor.expanded,
            onDismissRequest = interactor.onDismissRequest,

            // TODO add rest of popup properties
            properties = PopupProperties(focusable = interactor.focusable),
            modifier = combinedModifier,
            offset = presenter.offset,
        ) {
            iconProjectors.forEach { icon -> icon.Projection() }
        }
    }
}