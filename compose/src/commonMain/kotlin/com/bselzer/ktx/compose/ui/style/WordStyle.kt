package com.bselzer.ktx.compose.ui.style

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.bselzer.ktx.function.objects.merge

/**
 * CompositionLocal containing the preferred WordStyle that will be used by Text components by default.
 */
val LocalWordStyle: ProvidableCompositionLocal<WordStyle> = compositionLocalOf(structuralEqualityPolicy()) { WordStyle.Default }

/**
 * A wrapper around the standard [Text] composable.
 *
 * @param text the text to be displayed.
 * @param style the style describing how to lay out the text
 * @param onTextLayout callback that is executed when a new text layout is calculated. A TextLayoutResult object that callback provides contains paragraph information, size of the text, baselines and other details. The callback can be used to add additional decoration or functionality to the text. For example, to draw selection around the text.
 */
@Composable
fun Text(
    text: String,
    style: WordStyle = LocalWordStyle.current,
    onTextLayout: (TextLayoutResult) -> Unit = {}
) = androidx.compose.material.Text(
    text = text,
    modifier = style.modifier,
    color = style.color ?: Color.Unspecified,
    fontSize = style.fontSize ?: TextUnit.Unspecified,
    fontStyle = style.fontStyle,
    fontWeight = style.fontWeight,
    fontFamily = style.fontFamily,
    letterSpacing = style.letterSpacing ?: TextUnit.Unspecified,
    textDecoration = style.textDecoration,
    textAlign = style.textAlign,
    lineHeight = style.lineHeight ?: TextUnit.Unspecified,
    overflow = style.overflow ?: TextOverflow.Clip,
    softWrap = style.softWrap ?: true,
    maxLines = style.maxLines ?: Int.MAX_VALUE,
    onTextLayout = onTextLayout,
    style = style.textStyle ?: LocalTextStyle.current
)

/**
 * The style arguments associated with the [Text] composable.
 */
data class WordStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Color to apply to the text. If [Color.Unspecified], and style has no color set, this will be LocalContentColor.
     */
    val color: Color? = null,

    /**
     * The size of glyphs to use when painting the text. See [TextStyle.fontSize].
     */
    val fontSize: TextUnit? = null,

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
    val letterSpacing: TextUnit? = null,

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
    val lineHeight: TextUnit? = null,

    /**
     * How visual overflow should be handled.
     */
    val overflow: TextOverflow? = null,

    /**
     * Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If [softWrap] is false, [overflow] and TextAlign may have unexpected effects.
     */
    val softWrap: Boolean? = null,

    /**
     * The maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to [overflow] and [softWrap]. If it is not null, then it must be greater than zero.
     */
    val maxLines: Int? = null,

    /**
     * Style configuration for the text such as color, font, line height etc.
     */
    val textStyle: TextStyle? = null,
): ModifiableStyle<WordStyle> {
    companion object {
        @Stable
        val Default = WordStyle()
    }

    override fun merge(other: WordStyle?): WordStyle = if (other == null) this else WordStyle(
        modifier = modifier.then(other.modifier),
        color = color.merge(other.color),
        fontSize = fontSize.merge(other.fontSize),
        fontStyle = fontStyle.merge(other.fontStyle),
        fontWeight = fontWeight.merge(other.fontWeight),
        fontFamily = fontFamily.merge(other.fontFamily),
        letterSpacing = letterSpacing.merge(other.letterSpacing),
        textDecoration = textDecoration.merge(other.textDecoration),
        textAlign = textAlign.merge(other.textAlign),
        lineHeight = lineHeight.merge(other.lineHeight),
        overflow = overflow.merge(other.overflow),
        softWrap = softWrap.merge(other.softWrap),
        maxLines = maxLines.merge(other.maxLines),

        // Use the merge method on the object instead of the extension.
        textStyle = textStyle?.merge(other.textStyle) ?: other.textStyle,
    )
}