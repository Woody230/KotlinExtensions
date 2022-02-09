package com.bselzer.ktx.compose.ui.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.MaterialTheme
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.text.font.FontWeight
import com.bselzer.ktx.compose.ui.style.Text
import com.bselzer.ktx.compose.ui.style.WordStyle

/**
 * CompositionLocal containing the preferred TitleStyle that will be used by AppBarTitle components by default.
 */
val LocalTitleStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf {
    WordStyle.Default.copy(
        fontWeight = FontWeight.Bold,
        maxLines = 1
    )
}

/**
 * Lays out an app bar.
 *
 * @param title the title
 * @param titleStyle the text style of the title
 * @param navigationIcon the block for displaying the navigation icon
 * @param actions the block for displaying the remaining icons
 */
@Composable
fun MaterialAppBar(
    title: String,
    titleStyle: WordStyle = LocalTitleStyle.current,
    navigationIcon: (@Composable () -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {}
) = TopAppBar(
    title = { MaterialAppBarTitle(title = title, style = titleStyle) },
    navigationIcon = navigationIcon,
    actions = actions
)

/**
 * Lays out the title for a [MaterialAppBar].
 *
 * @param title the title
 * @param style the text style
 */
@Composable
fun MaterialAppBarTitle(title: String, style: WordStyle = LocalTitleStyle.current) =
    Text(
        text = title,
        style = WordStyle(
            textStyle = MaterialTheme.typography.h6,
        ).merge(style)
    )
