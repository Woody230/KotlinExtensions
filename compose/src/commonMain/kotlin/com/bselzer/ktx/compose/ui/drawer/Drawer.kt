package com.bselzer.ktx.compose.ui.drawer

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.container.DividedColumn

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
 * @param headerFontWeight the weight of the font of the [header]
 * @param headerColor the color of the text of the [header]
 * @param components the main content of this section
 */
@Composable
fun DrawerSection(
    modifier: Modifier = Modifier,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    header: String? = null,
    headerStyle: TextStyle = MaterialTheme.typography.caption,
    headerFontWeight: FontWeight = FontWeight.Bold,
    headerColor: Color = MaterialTheme.colors.primary,
    vararg components: @Composable ColumnScope.() -> Unit
) = DrawerSection(
    modifier = modifier,
    verticalArrangement = verticalArrangement,
    horizontalAlignment = horizontalAlignment
) {
    header?.let { header ->
        Spacer(modifier = Modifier.size(4.dp))
        Text(text = header, style = headerStyle, fontWeight = headerFontWeight, color = headerColor, maxLines = 1, overflow = TextOverflow.Ellipsis)
        Spacer(modifier = Modifier.size(4.dp))
    }

    DividedColumn(
        divider = { Spacer(modifier = Modifier.height(4.dp)) },
        contents = components,
    )
}

/**
 * Lays out a component of a drawer.
 *
 * @param modifier the modifier
 * @param verticalAlignment the vertical alignment of the content
 * @param horizontalArrangement the horizontal arrangement of the content
 * @param iconPainter the painter for displaying the icon image
 * @param text the title of the component
 * @param textStyle the style of the [text]
 * @param textFontWeight the weight of the font of the [text]
 * @param onClick the on-click handler
 */
@Composable
fun DrawerComponent(
    modifier: Modifier = Modifier,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
    iconPainter: Painter,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.body2,
    textFontWeight: FontWeight = FontWeight.Bold,
    onClick: () -> Unit
) = Row(
    verticalAlignment = verticalAlignment,
    horizontalArrangement = horizontalArrangement,
    modifier = Modifier
        .fillMaxWidth()
        .defaultMinSize(minHeight = 48.dp)
        .clickable { onClick() }
        .then(modifier)
) {
    Spacer(modifier = Modifier.width(16.dp))
    Icon(modifier = Modifier.size(24.dp), painter = iconPainter, contentDescription = text)
    Spacer(modifier = Modifier.width(32.dp))
    Text(text = text, style = textStyle, fontWeight = textFontWeight, maxLines = 1, overflow = TextOverflow.Ellipsis)
    Spacer(modifier = Modifier.width(8.dp))
}