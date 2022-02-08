package com.bselzer.ktx.compose.ui.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.container.DividedColumn
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.style.Text
import com.bselzer.ktx.compose.ui.style.WordStyle

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

    Spacer(modifier = Modifier.height(if (header == null) 16.dp else 8.dp))

    DividedColumn(
        divider = {
            Spacer(modifier = Modifier.height(4.dp))
            Divider(thickness = 1.dp)
            Spacer(modifier = Modifier.height(4.dp))
        },
        contents = sections,
    )
}

/**
 * Lays out a section of a drawer.
 *
 * @param modifier the modifier
 * @param verticalArrangement the vertical arrangement of the [content]
 * @param horizontalAlignment the horizontal alignment of the [content]
 * @param content the [content] to lay out
 */
@Composable
fun DrawerSection(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable ColumnScope.() -> Unit,
) = Column(
    modifier = Modifier
        .fillMaxWidth()
        .then(modifier),
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment,
    content = content
)

/**
 * Lays out a section of a drawer.
 *
 * @param modifier the modifier
 * @param verticalArrangement the vertical arrangement of the content
 * @param horizontalAlignment the horizontal alignment of the content
 * @param header the header describing the content of this section
 * @param headerStyle the style of the text of the [header]
 * @param components the main content of this section
 */
@Composable
fun DrawerSection(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: String? = null,
    headerStyle: WordStyle = WordStyle(),
    vararg components: @Composable ColumnScope.() -> Unit
) = DrawerSection(
    modifier = modifier,
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment
) {
    header?.let { header ->
        Spacer(height = 4.dp)

        val defaultText = WordStyle(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            textStyle = MaterialTheme.typography.subtitle2,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = header,
            style = defaultText.merge(headerStyle)
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
 * @param modifier the modifier
 * @param verticalAlignment the vertical alignment of the content
 * @param horizontalArrangement the horizontal arrangement of the content
 * @param onClick the on-click handler
 */
@Composable
fun DrawerComponentRow(
    modifier: Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) = Row(
    verticalAlignment = verticalAlignment,
    horizontalArrangement = horizontalArrangement,
    modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 48.dp)
        .clickable { onClick() }
        .then(modifier),
    content = content
)

/**
 * Lays out a component of a drawer.
 *
 * @param modifier the modifier
 * @param verticalAlignment the vertical alignment of the content
 * @param horizontalArrangement the horizontal arrangement of the content
 * @param iconPainter the painter for displaying the icon image
 * @param text the title of the component
 * @param textStyle the style of the [text]
 * @param onClick the on-click handler
 */
@Composable
fun DrawerComponent(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    iconPainter: Painter?,
    text: String,
    textStyle: WordStyle = WordStyle(),
    onClick: () -> Unit,
) = DrawerComponentRow(
    modifier = modifier,
    verticalAlignment = verticalAlignment,
    horizontalArrangement = horizontalArrangement,
    onClick = onClick
) {
    Spacer(width = 16.dp)
    iconPainter?.let {
        Icon(modifier = Modifier.size(24.dp), painter = iconPainter, contentDescription = text)
        Spacer(width = 32.dp)
    }

    val defaultText = WordStyle(
        textStyle = MaterialTheme.typography.body2,
        fontWeight = FontWeight.Bold,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
    Text(text = text, style = defaultText.merge(textStyle))
    Spacer(width = 8.dp)
}