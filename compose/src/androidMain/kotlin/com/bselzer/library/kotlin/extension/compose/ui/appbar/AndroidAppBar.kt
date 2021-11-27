package com.bselzer.library.kotlin.extension.compose.ui.appbar

import androidx.annotation.StringRes
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

/**
 * Displays the title for a [MaterialAppBar].
 *
 * @param title the title
 * @param fontWeight the font weight
 * @param style the text style
 */
@Composable
fun MaterialAppBarTitle(@StringRes title: Int, style: TextStyle = LocalTextStyle.current, fontWeight: FontWeight = FontWeight.Bold) =
    com.bselzer.library.kotlin.extension.compose.ui.appbar.MaterialAppBarTitle(
        title = stringResource(id = title),
        style = style, fontWeight = fontWeight
    )