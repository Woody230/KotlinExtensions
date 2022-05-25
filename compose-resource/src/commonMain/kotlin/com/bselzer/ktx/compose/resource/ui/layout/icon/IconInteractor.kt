package com.bselzer.ktx.compose.resource.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.state.ToggleableState
import com.bselzer.ktx.compose.resource.images.painter
import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.localized
import dev.icerock.moko.resources.desc.desc

/**
 * Creates an interactor for indicating the deletion of an element.
 */
@Composable
fun deleteIconInteractor() = IconInteractor(
    painter = Icons.Filled.Delete.painter(),
    contentDescription = Resources.strings.delete.desc().localized(),
)

/**
 * Creates an interactor for an up-directional icon.
 */
@Composable
fun upIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowUp.painter(),
    contentDescription = Resources.strings.up.desc().localized(),
)

/**
 * Creates an interactor for a down-directional icon.
 */
@Composable
fun downIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowDown.painter(),
    contentDescription = Resources.strings.down.desc().localized(),
)

/**
 * Creates an interactor for a left-directional icon.
 */
@Composable
fun leftIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowLeft.painter(),
    contentDescription = Resources.strings.left.desc().localized(),
)

/**
 * Creates an interactor for a right-directional icon.
 */
@Composable
fun rightIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowRight.painter(),
    contentDescription = Resources.strings.right.desc().localized(),
)

/**
 * Creates an interactor for indicating a dropdown menu.
 */
@Composable
fun dropdownIconInteractor() = IconInteractor(
    painter = Icons.Filled.MoreVert.painter(),
    contentDescription = Resources.strings.more_options.desc().localized(),
)

/**
 * Creates an interactor for indicating the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 */
@Composable
fun expansionIconInteractor(isExpanded: Boolean) = IconInteractor(
    painter = if (isExpanded) Icons.Filled.KeyboardArrowUp.painter() else Icons.Filled.KeyboardArrowDown.painter(),
    contentDescription = run {
        val resource = if (isExpanded) Resources.strings.expand else Resources.strings.condense
        resource.desc().localized()
    }
)

/**
 * Creates an interactor for indicating the navigation up the hierarchy.
 */
@Composable
fun upNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.ArrowBack.painter(),
    contentDescription = Resources.strings.up.desc().localized(),
)

/**
 * Creates an interactor for indicating a navigation drawer.
 */
@Composable
fun drawerNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.Menu.painter(),
    contentDescription = Resources.strings.menu.desc().localized(),
)

/**
 * Creates an interactor for indicating the refresh of an element.
 */
@Composable
fun refreshIconInteractor() = IconInteractor(
    painter = Icons.Filled.Refresh.painter(),
    contentDescription = Resources.strings.refresh.desc().localized(),
)

/**
 * Creates an interactor for indicating the use of a cache.
 */
@Composable
fun cachedIconInteractor() = IconInteractor(
    painter = Resources.images.ic_cached.painter(),
    contentDescription = Resources.strings.cache.desc().localized()
)

/**
 * Creates an interactor for indicating the use of settings.
 */
@Composable
fun settingsIconInteractor() = IconInteractor(
    painter = Resources.images.ic_settings.painter(),
    contentDescription = Resources.strings.settings.desc().localized()
)

/**
 * Creates an interactor for indicating the zooming in of a map.
 */
@Composable
fun zoomInMapIconInteractor() = IconInteractor(
    painter = Resources.images.ic_zoom_in_map.painter(),
    contentDescription = Resources.strings.zoom_in.desc().localized()
)

/**
 * Creates an interactor for indicating the zooming out of a map.
 */
@Composable
fun zoomOutMapIconInteractor() = IconInteractor(
    painter = Resources.images.ic_zoom_out_map.painter(),
    contentDescription = Resources.strings.zoom_out.desc().localized()
)

/**
 * Creates an interactor for indicating the use of content licenses.
 */
@Composable
fun licenseIconInteractor() = IconInteractor(
    painter = Resources.images.ic_policy.painter(),
    contentDescription = Resources.strings.licenses.desc().localized()
)

/**
 * Creates an interactor for indicating information about the application.
 */
@Composable
fun aboutAppIconInteractor() = IconInteractor(
    painter = Resources.images.ic_info.painter(),
    contentDescription = Resources.strings.about_app.desc().localized()
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
    contentDescription = when (state) {
        ToggleableState.Indeterminate -> Resources.strings.partially_selected
        ToggleableState.Off -> Resources.strings.not_selected
        ToggleableState.On -> Resources.strings.selected
    }.desc().localized()
)

/**
 * Creates an interactor for indicating language for internationalization.
 */
@Composable
fun languageIconInteractor() = IconInteractor(
    painter = Resources.images.ic_language.painter(),
    contentDescription = Resources.strings.language.desc().localized()
)