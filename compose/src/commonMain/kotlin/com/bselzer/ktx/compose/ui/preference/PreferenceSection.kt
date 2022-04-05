package com.bselzer.ktx.compose.ui.preference

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.Dp
import com.bselzer.ktx.compose.ui.container.DividedColumn
import com.bselzer.ktx.compose.ui.container.DividedRow
import com.bselzer.ktx.compose.ui.container.Spacer
import com.bselzer.ktx.compose.ui.description.DescriptionTitle
import com.bselzer.ktx.compose.ui.style.*

/**
 * Lays out a [title] for a section of preferences.
 *
 * @param style the style describing how to lay out the section title
 * @param spacing the spacing between components
 * @param painter the painter for displaying the image
 * @param imageStyle the style describing how to lay out the image
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 */
@Composable
fun PreferenceSectionTitle(
    style: RowStyle = LocalRowStyle.current,
    spacing: Dp = PreferenceSpacing,
    painter: Painter,
    imageStyle: ImageStyle = LocalImageStyle.current,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current
) = DividedRow(
    style = RowStyle(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ).merge(style),
    divider = { Spacer(width = spacing) },
    contents = arrayOf(
        {
            PreferenceImage(
                painter = painter,
                contentDescription = title,
                style = imageStyle
            )
        },
        {
            DescriptionTitle(
                title = title,
                style = WordStyle(
                    color = MaterialTheme.colors.primary
                ).merge(titleStyle)
            )
        }
    )
)

/**
 * Lays out a section of preferences with a [title].
 *
 * @param style the style describing how to lay out the section
 * @param painter the painter for displaying the image
 * @param imageStyle the style describing how to lay out the image
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleSpacing the spacing within the components of the [title]
 * @param spacing the spacing between the [title] and the [content]
 */
@Composable
fun PreferenceSection(
    style: ColumnStyle = LocalColumnStyle.current,
    painter: Painter,
    imageStyle: ImageStyle = LocalImageStyle.current,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    titleSpacing: Dp = PreferenceSpacing,
    spacing: Dp = PreferenceSectionSpacing,
    content: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    style = style,
    divider = { Spacer(height = spacing) },
    contents = arrayOf(
        {
            PreferenceSectionTitle(
                spacing = titleSpacing,
                title = title,
                painter = painter,
                imageStyle = imageStyle,
                titleStyle = titleStyle
            )
        },
        {
            content()
        }
    )
)

/**
 * Lays out a [title] for a section of preferences.
 *
 * @param spacing the spacing between components
 * @param title the name of the preference section
 * @param style the style describing how to lay out the title
 */
@Composable
fun PreferenceSectionTitle(
    spacing: Dp = PreferenceSpacing + PreferenceImageSize, // Default spacing + default image size
    title: String,
    style: WordStyle = LocalWordStyle.current,
) = DescriptionTitle(
    title = title,
    style = WordStyle(
        modifier = Modifier.padding(start = spacing),
        color = MaterialTheme.colors.primary
    ).merge(style)
)

/**
 * Lays out a section of preferences with a [title].
 *
 * @param style the style describing how to lay out the section
 * @param title the name of the preference section
 * @param titleStyle the style of the text for displaying the [title]
 * @param titleSpacing the spacing within the components of the [title]
 * @param spacing the spacing between the [title] and the [content]
 */
@Composable
fun PreferenceSection(
    style: ColumnStyle = LocalColumnStyle.current,
    title: String,
    titleStyle: WordStyle = LocalWordStyle.current,
    titleSpacing: Dp = PreferenceSpacing + PreferenceImageSize,
    spacing: Dp = PreferenceSectionSpacing,
    content: @Composable ColumnScope.() -> Unit
) = DividedColumn(
    style = style,
    divider = { Spacer(height = spacing) },
    contents = arrayOf(
        {
            PreferenceSectionTitle(
                spacing = titleSpacing,
                title = title,
                style = titleStyle,
            )
        },
        {
            content()
        }
    )
)