package com.bselzer.ktx.compose.ui.container

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * Lays out space between items in a row.
 */
@Composable
fun RowScope.Spacer(width: Dp) = Spacer(modifier = Modifier.width(width = width)) // TODO context receiver

/**
 * Lays out space between items in a column.
 */
@Composable
fun ColumnScope.Spacer(height: Dp) = Spacer(modifier = Modifier.height(height = height)) // TODO context receiver