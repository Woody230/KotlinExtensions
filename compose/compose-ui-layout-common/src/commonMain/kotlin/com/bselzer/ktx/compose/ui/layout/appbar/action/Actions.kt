package com.bselzer.ktx.compose.ui.layout.appbar.action

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.*
import com.bselzer.ktx.compose.ui.layout.dropdown.menu.DropdownMenuInteractor
import com.bselzer.ktx.compose.ui.layout.dropdown.menu.DropdownMenuPresenter
import com.bselzer.ktx.compose.ui.layout.dropdown.menu.DropdownMenuProjector
import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonInteractor
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonPresenter
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector

@Composable
internal fun RowScope.Actions(
    actions: List<IconButtonInteractor>,
    dropdown: IconInteractor?,
    presenter: IconButtonPresenter,
    policy: ActionPolicy
) {
    // Currently just prioritize actions to show on the bar according to the order in the list.
    // TODO explicit prioritization like Android? https://developer.android.com/guide/topics/resources/menu-resource#item-element
    //  showAsAction: always, never, if room, with text
    val maxActions = policy.maxActions
    val numOnBar = when {
        dropdown == null -> actions.size
        actions.size <= maxActions -> actions.size

        // Will need to add the dropdown icon so display one fewer action on the bar.
        else -> maxActions - 1
    }

    val onBar = actions.subList(0, numOnBar)
    val inOverflow = actions.subList(numOnBar, actions.size)

    onBar.forEach { action -> IconButtonProjector(action, presenter).Projection() }

    // If there is overflow, then display the icon used to control whether the dropdown should be displayed.
    if (inOverflow.any() && dropdown != null) {
        var isExpanded by remember { mutableStateOf(false) }
        Box {
            IconButtonProjector(
                interactor = IconButtonInteractor(
                    icon = dropdown,
                    onClick = { isExpanded = !isExpanded }
                ),
                presenter = presenter
            ).Projection()

            DropdownMenuProjector(
                interactor = DropdownMenuInteractor(
                    icons = inOverflow,
                    expanded = isExpanded,
                    onDismissRequest = { isExpanded = false }
                ),
                presenter = DropdownMenuPresenter(icon = presenter)
            ).Projection()
        }
    }
}