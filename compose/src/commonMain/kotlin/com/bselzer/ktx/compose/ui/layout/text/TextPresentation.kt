package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class TextPresentation(
    /**
     * Color to apply to the text. If [Color.Unspecified], and style has no color set, this will be LocalContentColor.
     */
    val color: Color = Color.Unspecified,

    /**
     * The size of glyphs to use when painting the text. See [TextStyle.fontSize].
     */
    val fontSize: TextUnit = TextUnit.Unspecified,

    /**
     * The typeface variant to use when drawing the letters (e.g., italic). See [TextStyle.fontStyle].
     */
    val fontStyle: FontStyle? = null,

    /**
     * The typeface thickness to use when painting the text (e.g., [FontWeight.Bold]).
     */
    val fontWeight: FontWeight? = null,

    /**
     * The font family to be used when rendering the text. See [TextStyle.fontFamily].
     */
    val fontFamily: FontFamily? = null,

    /**
     * The amount of space to add between each letter. See [TextStyle.letterSpacing].
     */
    val letterSpacing: TextUnit = TextUnit.Unspecified,

    /**
     * The decorations to paint on the text (e.g., an underline). See [TextStyle.textDecoration].
     */
    val textDecoration: TextDecoration? = null,

    /**
     * The alignment of the text within the lines of the paragraph. See [TextStyle.textAlign].
     */
    val textAlign: TextAlign? = null,

    /**
     * Line height for the Paragraph in TextUnit unit, e.g. SP or EM. See [TextStyle.lineHeight].
     */
    val lineHeight: TextUnit = TextUnit.Unspecified,

    /**
     * How visual overflow should be handled.
     */
    val overflow: TextOverflow = TextOverflow.Clip,

    /**
     * Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If [softWrap] is false, [overflow] and TextAlign may have unexpected effects.
     */
    val softWrap: Boolean = true,

    /**
     * The maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to [overflow] and [softWrap]. If it is not null, then it must be greater than zero.
     */
    val maxLines: Int = Int.MAX_VALUE,

    /**
     * Style configuration for the text such as color, font, line height etc.
     */
    val textStyle: TextStyle = TextStyle.Default,
) : PresentationModel