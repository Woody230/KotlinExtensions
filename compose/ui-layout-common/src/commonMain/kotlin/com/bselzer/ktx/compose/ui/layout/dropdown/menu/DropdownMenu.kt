package com.bselzer.ktx.compose.ui.layout.dropdown.menu

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpOffset

// TODO remove when DropdownMenu is able to accessed in common https://github.com/JetBrains/compose-multiplatform/issues/762
@Composable
internal expect fun DropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    focusable: Boolean,
    modifier: Modifier,
    offset: DpOffset,
    content: @Composable ColumnScope.() -> Unit
)