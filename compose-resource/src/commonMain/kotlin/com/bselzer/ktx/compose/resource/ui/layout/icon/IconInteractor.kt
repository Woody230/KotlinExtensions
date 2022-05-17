package com.bselzer.ktx.compose.resource.ui.layout.icon

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
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
    contentDescription = stringResource(resource = if (isExpanded) Resources.strings.condense else Resources.strings.expand),
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