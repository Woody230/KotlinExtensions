package com.bselzer.ktx.compose.ui.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.container.DividedColumn
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.style.*

/**
 * CompositionLocal containing the preferred WordStyle that will be used by Header components by default.
 */
val LocalHeaderStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf {
    WordStyle.Default.copy(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

/**
 * CompositionLocal containing the preferred WordStyle that will be used by Text components by default.
 */
val LocalWordStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf {
    WordStyle.Default.copy(
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

/**
 * CompositionLocal containing the preferred RowStyle that will be used by Row components by default.
 */
val LocalRowStyle: ProvidableCompositionLocal<RowStyle> = compositionLocalOf {
    RowStyle.Default.copy(
        modifier = Modifier.fillMaxWidth().defaultMinSize(minHeight = 48.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    )
}

/**
 * Lays out the drawer content with a [header] and divided [sections].
 *
 * @param header the header describing the drawer content
 * @param sections the content collections
 */
@Composable
fun ColumnScope.MaterialDrawerContent(
    header: (@Composable () -> Unit)? = null,
    vararg sections: @Composable ColumnScope.() -> Unit,
) {
    header?.let {
        header()
    }

    Spacer(height = if (header == null) 16.dp else 8.dp)

    DividedColumn(
        divider = {
            Spacer(height= 4.dp)
            Divider(thickness = 1.dp)
            Spacer(height = 4.dp)
        },
        contents = sections,
    )
}

/**
 * Lays out a section of a drawer.
 *
 * @param style the style describing how to lay out the column
 * @param content the [content] to lay out
 */
@Composable
fun DrawerSection(
    style: ColumnStyle = LocalColumnStyle.current,
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    style = ColumnStyle(modifier = Modifier.fillMaxWidth()).merge(style),
    content = content
)

/**
 * Lays out a section of a drawer.
 *
 * @param style the style describing how to lay out the column
 * @param header the header describing the content of this section
 * @param headerStyle the style of the text of the [header]
 * @param components the main content of this section
 */
@Composable
fun DrawerSection(
    style: ColumnStyle = LocalColumnStyle.current,
    header: String? = null,
    headerStyle: WordStyle = LocalHeaderStyle.current,
    vararg components: @Composable ColumnScope.() -> Unit
) = DrawerSection(
    style = style
) {
    header?.let { header ->
        Spacer(height = 4.dp)

        Text(
            text = header,
            style = WordStyle(
                textStyle = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.primary,
            ).merge(headerStyle)
        )

        Spacer(height = 4.dp)
    }

    DividedColumn(
        divider = { Spacer(height = 4.dp) },
        contents = components,
    )
}

/**
 * Lays out the row for a component of a drawer.
 *
 * @param style the style describing how to lay out the row
 * @param onClick the on-click handler
 */
@Composable
fun DrawerComponentRow(
    style: RowStyle = LocalRowStyle.current,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) = Row(
    style = RowStyle(modifier = Modifier.clickable { onClick() }).merge(style),
    content = content
)

/**
 * Lays out a component of a drawer.
 *
 * @param style the style describing how to lay out the row
 * @param icon the painter for displaying the icon image
 * @param text the title of the component
 * @param textStyle the style of the [text]
 * @param onClick the on-click handler
 */
@Composable
fun DrawerComponent(
    style: RowStyle = LocalRowStyle.current,
    icon: Painter?,
    text: String,
    textStyle: WordStyle = LocalWordStyle.current,
    onClick: () -> Unit,
) = DrawerComponentRow(
    style = style,
    onClick = onClick
) {
    Spacer(width = 16.dp)

    icon?.let {
        Icon(modifier = Modifier.size(24.dp), painter = icon, contentDescription = text)
        Spacer(width = 32.dp)
    }

    Text(
        text = text,
        style = WordStyle(
            textStyle = MaterialTheme.typography.body2,
        ).merge(textStyle)
    )

    Spacer(width = 8.dp)
}