package com.bselzer.ktx.compose.resource.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.state.ToggleableState
import com.bselzer.ktx.compose.resource.images.painter
import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Creates an interactor for indicating the deletion of an element.
 */
@Composable
fun deleteIconInteractor() = IconInteractor(
    painter = Icons.Filled.Delete.painter(),
    contentDescription = stringResource(resource = Resources.strings.delete),
)

/**
 * Creates an interactor for an up-directional icon.
 */
@Composable
fun upIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowUp.painter(),
    contentDescription = stringResource(Resources.strings.up),
)

/**
 * Creates an interactor for a down-directional icon.
 */
@Composable
fun downIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowDown.painter(),
    contentDescription = stringResource(Resources.strings.down),
)

/**
 * Creates an interactor for a left-directional icon.
 */
@Composable
fun leftIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowLeft.painter(),
    contentDescription = stringResource(Resources.strings.left),
)

/**
 * Creates an interactor for a right-directional icon.
 */
@Composable
fun rightIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowRight.painter(),
    contentDescription = stringResource(Resources.strings.right),
)

/**
 * Creates an interactor for indicating a dropdown menu.
 */
@Composable
fun dropdownIconInteractor() = IconInteractor(
    painter = Icons.Filled.MoreVert.painter(),
    contentDescription = stringResource(resource = Resources.strings.more_options),
)

/**
 * Creates an interactor for indicating the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 */
@Composable
fun expansionIconInteractor(isExpanded: Boolean) = IconInteractor(
    painter = if (isExpanded) Icons.Filled.KeyboardArrowUp.painter() else Icons.Filled.KeyboardArrowDown.painter(),
    contentDescription = stringResource(resource = if (isExpanded) Resources.strings.expand else Resources.strings.condense),
)

/**
 * Creates an interactor for indicating the navigation up the hierarchy.
 */
@Composable
fun upNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.ArrowBack.painter(),
    contentDescription = stringResource(resource = Resources.strings.up),
)

/**
 * Creates an interactor for indicating a navigation drawer.
 */
@Composable
fun drawerNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.Menu.painter(),
    contentDescription = stringResource(resource = Resources.strings.menu),
)

/**
 * Creates an interactor for indicating the refresh of an element.
 */
@Composable
fun refreshIconInteractor() = IconInteractor(
    painter = Icons.Filled.Refresh.painter(),
    contentDescription = stringResource(resource = Resources.strings.refresh),
)

/**
 * Creates an interactor for indicating the use of a cache.
 */
@Composable
fun cachedIconInteractor() = IconInteractor(
    painter = Resources.images.ic_cached.painter(),
    contentDescription = stringResource(Resources.strings.cache)
)

/**
 * Creates an interactor for indicating the use of settings.
 */
@Composable
fun settingsIconInteractor() = IconInteractor(
    painter = Resources.images.ic_settings.painter(),
    contentDescription = stringResource(Resources.strings.settings)
)

/**
 * Creates an interactor for indicating the zooming in of a map.
 */
@Composable
fun zoomInMapIconInteractor() = IconInteractor(
    painter = Resources.images.ic_zoom_in_map.painter(),
    contentDescription = stringResource(Resources.strings.zoom_in)
)

/**
 * Creates an interactor for indicating the zooming out of a map.
 */
@Composable
fun zoomOutMapIconInteractor() = IconInteractor(
    painter = Resources.images.ic_zoom_out_map.painter(),
    contentDescription = stringResource(Resources.strings.zoom_out)
)

/**
 * Creates an interactor for indicating the use of content licenses.
 */
@Composable
fun licenseIconInteractor() = IconInteractor(
    painter = Resources.images.ic_policy.painter(),
    contentDescription = stringResource(Resources.strings.licenses)
)

/**
 * Creates an interactor for indicating information about the application.
 */
@Composable
fun aboutAppIconInteractor() = IconInteractor(
    painter = Resources.images.ic_info.painter(),
    contentDescription = stringResource(Resources.strings.about_app)
)

/**
 * Creates an interactor for indicating the on, off, or indeterminate state of a tri-state checkbox.
 */
@Composable
fun triStateCheckboxIconInteractor(state: ToggleableState) = IconInteractor(
    painter = when (state) {
        ToggleableState.Indeterminate -> Resources.images.ic_checkbox_indeterminate
        ToggleableState.Off -> Resources.images.ic_checkbox_outline_blank
        ToggleableState.On -> Resources.images.ic_checkbox
    }.painter(),
    contentDescription = stringResource(
        when (state) {
            ToggleableState.Indeterminate -> Resources.strings.partially_selected
            ToggleableState.Off -> Resources.strings.not_selected
            ToggleableState.On -> Resources.strings.selected
        }
    )
)