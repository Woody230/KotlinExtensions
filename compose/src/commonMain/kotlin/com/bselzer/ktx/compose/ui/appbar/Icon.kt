package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Lays out a refresh icon for an app bar.
 *
 * @param modifier the button modifier
 * @param onClick the on-click handler
 */
@Composable
fun RefreshIcon(modifier: Modifier = Modifier, onClick: suspend CoroutineScope.() -> Unit) {
    val scope = rememberCoroutineScope()
    IconButton(modifier = modifier, onClick = { scope.launch { onClick(scope) } }) {
        Icon(Icons.Filled.Refresh, contentDescription = "Refresh")
    }
}

/**
 * Lays out a delete icon for an app bar.
 *
 * @param modifier the button modifier
 * @param enabled whether the on-click handler is enabled
 * @param onClick the on-click handler
 */
@Composable
fun DeleteIcon(modifier: Modifier = Modifier, enabled: Boolean = true, onClick: suspend CoroutineScope.() -> Unit) {
    val scope = rememberCoroutineScope()
    IconButton(modifier = modifier, enabled = enabled, onClick = { scope.launch { onClick(scope) } }) {
        Icon(Icons.Filled.Delete, contentDescription = "Delete")
    }
}

/**
 * Lays out an icon for displaying a dropdown menu for an app bar.
 *
 * @param modifier the button modifier
 * @param setExpanded the block for setting the current state of expansion
 */
@Composable
fun DropdownIcon(modifier: Modifier = Modifier, setExpanded: (Boolean) -> Unit) = IconButton(modifier = modifier, onClick = { setExpanded(true) }) {
    Icon(Icons.Filled.MoreVert, contentDescription = "More Options")
}

/**
 * Lays out an icon for representing the expansion or compression of another element.
 *
 * @param modifier the button modifier
 * @param state the state of the expansion
 */
@Composable
fun ExpansionIcon(modifier: Modifier = Modifier, state: MutableState<Boolean>) {
    val isExpanded = remember { state }.value
    IconButton(modifier = modifier, onClick = { state.value = !isExpanded }) {
        Icon(
            imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (isExpanded) "Condense" else "Expand"
        )
    }
}