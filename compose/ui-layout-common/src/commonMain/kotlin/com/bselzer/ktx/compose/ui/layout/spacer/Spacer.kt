package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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