package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.state.ToggleableState
import com.bselzer.ktx.resource.KtxResources
import com.bselzer.ktx.resource.images.painter
import com.bselzer.ktx.resource.strings.localized

/**
 * Creates an interactor for indicating the deletion of an element.
 */
@Composable
fun deleteIconInteractor() = IconInteractor(
    painter = Icons.Filled.Delete.painter(),
    contentDescription = KtxResources.strings.delete.localized(),
)

/**
 * Creates an interactor for an up-directional icon.
 */
@Composable
fun upIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowUp.painter(),
    contentDescription = KtxResources.strings.up.localized(),
)

/**
 * Creates an interactor for a down-directional icon.
 */
@Composable
fun downIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowDown.painter(),
    contentDescription = KtxResources.strings.down.localized(),
)

/**
 * Creates an interactor for a left-directional icon.
 */
@Composable
fun leftIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowLeft.painter(),
    contentDescription = KtxResources.strings.left.localized(),
)

/**
 * Creates an interactor for a right-directional icon.
 */
@Composable
fun rightIconInteractor() = IconInteractor(
    painter = Icons.Filled.KeyboardArrowRight.painter(),
    contentDescription = KtxResources.strings.right.localized(),
)

/**
 * Creates an interactor for indicating a dropdown menu.
 */
@Composable
fun dropdownIconInteractor() = IconInteractor(
    painter = Icons.Filled.MoreVert.painter(),
    contentDescription = KtxResources.strings.more_options.localized(),
)

/**
 * Creates an interactor for indicating the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 */
@Composable
fun expansionIconInteractor(isExpanded: Boolean) = IconInteractor(
    painter = if (isExpanded) Icons.Filled.KeyboardArrowDown.painter() else Icons.Filled.KeyboardArrowRight.painter(),
    contentDescription = run {
        val resource = if (isExpanded) KtxResources.strings.expand else KtxResources.strings.condense
        resource.localized()
    }
)

/**
 * Creates an interactor for indicating the navigation up the hierarchy.
 */
@Composable
fun upNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.ArrowBack.painter(),
    contentDescription = KtxResources.strings.up.localized(),
)

/**
 * Creates an interactor for indicating a navigation drawer.
 */
@Composable
fun drawerNavigationIconInteractor() = IconInteractor(
    painter = Icons.Filled.Menu.painter(),
    contentDescription = KtxResources.strings.menu.localized(),
)

/**
 * Creates an interactor for indicating the refresh of an element.
 */
@Composable
fun refreshIconInteractor() = IconInteractor(
    painter = Icons.Filled.Refresh.painter(),
    contentDescription = KtxResources.strings.refresh.localized(),
)

/**
 * Creates an interactor for indicating the use of a cache.
 */
@Composable
fun cachedIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_cached.painter(),
    contentDescription = KtxResources.strings.cache.localized()
)

/**
 * Creates an interactor for indicating the use of settings.
 */
@Composable
fun settingsIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_settings.painter(),
    contentDescription = KtxResources.strings.settings.localized()
)

/**
 * Creates an interactor for indicating the zooming in of a map.
 */
@Composable
fun zoomInMapIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_zoom_in_map.painter(),
    contentDescription = KtxResources.strings.zoom_in.localized()
)

/**
 * Creates an interactor for indicating the zooming out of a map.
 */
@Composable
fun zoomOutMapIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_zoom_out_map.painter(),
    contentDescription = KtxResources.strings.zoom_out.localized()
)

/**
 * Creates an interactor for indicating the use of content licenses.
 */
@Composable
fun licenseIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_policy.painter(),
    contentDescription = KtxResources.strings.licenses.localized()
)

/**
 * Creates an interactor for indicating information about the application.
 */
@Composable
fun aboutAppIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_info.painter(),
    contentDescription = KtxResources.strings.about_app.localized()
)

/**
 * Creates an interactor for indicating the on, off, or indeterminate state of a tri-state checkbox.
 */
@Composable
fun triStateCheckboxIconInteractor(state: ToggleableState) = IconInteractor(
    painter = when (state) {
        ToggleableState.Indeterminate -> KtxResources.images.ic_checkbox_indeterminate
        ToggleableState.Off -> KtxResources.images.ic_checkbox_outline_blank
        ToggleableState.On -> KtxResources.images.ic_checkbox
    }.painter(),
    contentDescription = when (state) {
        ToggleableState.Indeterminate -> KtxResources.strings.partially_selected
        ToggleableState.Off -> KtxResources.strings.not_selected
        ToggleableState.On -> KtxResources.strings.selected
    }.localized()
)

/**
 * Creates an interactor for indicating language for internationalization.
 */
@Composable
fun languageIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_language.painter(),
    contentDescription = KtxResources.strings.language.localized()
)

/**
 * Creates an interactor for indicating a color picker.
 */
@Composable
fun colorPickerIconInteractor() = IconInteractor(
    painter = KtxResources.images.ic_color_lens.painter(),
    contentDescription = KtxResources.strings.color.localized()
)