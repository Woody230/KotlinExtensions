package com.bselzer.library.kotlin.extension.compose.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Displays an app bar.
 *
 * @param title the title
 * @param titleStyle the text style of the title
 * @param navigationIcon the block for displaying the navigation icon
 * @param actions the block for displaying the remaining icons
 */
@Composable
fun MaterialAppBar(title: String, titleStyle: TextStyle = LocalTextStyle.current, navigationIcon: @Composable () -> Unit = {}, actions: @Composable RowScope.() -> Unit = {}) =
    TopAppBar(
        title = { MaterialAppBarTitle(title = title, style = titleStyle) },
        navigationIcon = navigationIcon,
        actions = actions
    )

/**
 * Displays the title for a [MaterialAppBar].
 *
 * @param title the title
 * @param fontWeight the font weight
 * @param style the text style
 */
@Composable
fun MaterialAppBarTitle(title: String, style: TextStyle = LocalTextStyle.current, fontWeight: FontWeight = FontWeight.Bold) =
    Text(text = title, fontWeight = fontWeight, style = style, maxLines = 1)