package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.style.LocalWordStyle
import com.bselzer.ktx.compose.ui.style.WordStyle

/**
 * Wraps a [MaterialAppBar] and the [content] in a [Column].
 *
 * @param title the title
 * @param titleStyle the text style of the title
 * @param navigationIcon the block for displaying the navigation icon
 * @param actions the block for displaying the remaining icons
 */
@Composable
fun MaterialAppBarColumn(
    modifier: Modifier = Modifier,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxSize()
        .then(modifier)
) {
    MaterialAppBar(title = title, titleStyle = titleStyle, navigationIcon = navigationIcon, actions = actions)
    content()
}