package com.bselzer.library.kotlin.extension.compose.ui.appbar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Displays an app bar.
 *
 * @param title the title resource
 * @param titleStyle the text style of the title
 * @param navigationIcon the block for displaying the navigation icon
 * @param actions the block for displaying the remaining icons
 */
@Composable
fun MaterialAppBar(
    @StringRes title: Int,
    titleStyle: TextStyle = LocalTextStyle.current,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {}
) =
    MaterialAppBar(title = stringResource(id = title), titleStyle = titleStyle, navigationIcon = navigationIcon, actions = actions)

/**
 * Displays the title for a [MaterialAppBar].
 *
 * @param title the title resource
 * @param fontWeight the font weight
 * @param style the text style
 */
@Composable
fun MaterialAppBarTitle(@StringRes title: Int, style: TextStyle = LocalTextStyle.current, fontWeight: FontWeight = FontWeight.Bold) =
    MaterialAppBarTitle(
        title = stringResource(id = title),
        style = style, fontWeight = fontWeight
    )