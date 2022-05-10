package com.bselzer.ktx.compose.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import com.bselzer.ktx.resource.Resources
import dev.icerock.moko.resources.compose.stringResource

/**
 * Creates an interactor for indicating the deletion of an element.
 */
@Composable
fun deleteIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.Delete,
    contentDescription = stringResource(resource = Resources.strings.delete),
)

/**
 * Creates an interactor for an up-directional icon.
 */
@Composable
fun upIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.KeyboardArrowUp,
    contentDescription = stringResource(Resources.strings.up),
)

/**
 * Creates an interactor for a down-directional icon.
 */
@Composable
fun downIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.KeyboardArrowDown,
    contentDescription = stringResource(Resources.strings.down),
)

/**
 * Creates an interactor for a left-directional icon.
 */
@Composable
fun leftIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.KeyboardArrowLeft,
    contentDescription = stringResource(Resources.strings.left),
)

/**
 * Creates an interactor for a right-directional icon.
 */
@Composable
fun rightIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.KeyboardArrowRight,
    contentDescription = stringResource(Resources.strings.right),
)

/**
 * Creates an interactor for indicating a dropdown menu.
 */
@Composable
fun dropdownIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.MoreVert,
    contentDescription = stringResource(resource = Resources.strings.more_options),
)

/**
 * Creates an interactor for indicating the expansion or compression of another element.
 *
 * @param isExpanded whether the element is expanded
 */
@Composable
fun expansionIconInteractor(isExpanded: Boolean) = IconInteractor(
    imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
    contentDescription = stringResource(resource = if (isExpanded) Resources.strings.condense else Resources.strings.expand),
)

/**
 * Creates an interactor for indicating the navigation up the hierarchy.
 */
@Composable
fun upNavigationIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.ArrowBack,
    contentDescription = stringResource(resource = Resources.strings.up),
)

/**
 * Creates an interactor for indicating a navigation drawer.
 */
@Composable
fun drawerNavigationIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.Menu,
    contentDescription = stringResource(resource = Resources.strings.menu),
)

/**
 * Creates an interactor for indicating the refresh of an element.
 */
@Composable
fun refreshIconInteractor() = IconInteractor(
    imageVector = Icons.Filled.Refresh,
    contentDescription = stringResource(resource = Resources.strings.refresh),
)