package com.bselzer.ktx.compose.ui.layout.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.function.objects.nullMerge
import com.bselzer.ktx.function.objects.safeMerge

data class TextPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,

    /**
     * Color to apply to the text. If [Color.Unspecified], and style has no color set, this will be LocalContentColor.
     */
    val color: Color = ComposeMerger.color.default,

    /**
     * The size of glyphs to use when painting the text. See [TextStyle.fontSize].
     */
    val fontSize: TextUnit = ComposeMerger.textUnit.default,

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
    val letterSpacing: TextUnit = ComposeMerger.textUnit.default,

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
    val lineHeight: TextUnit = ComposeMerger.textUnit.default,

    /**
     * How visual overflow should be handled.
     */
    val overflow: TextOverflow = TextOverflow.Clip,

    /**
     * Whether the text should break at soft line breaks. If false, the glyphs in the text will be positioned as if there was unlimited horizontal space. If [softWrap] is false, [overflow] and TextAlign may have unexpected effects.
     */
    val softWrap: TriState = ComposeMerger.triState.default,

    /**
     * The maximum number of lines for the text to span, wrapping if necessary. If the text exceeds the given number of lines, it will be truncated according to [overflow] and [softWrap]. If it is not null, then it must be greater than zero.
     */
    val maxLines: Int = ComposeMerger.int.default,

    /**
     * Style configuration for the text such as color, font, line height etc.
     */
    val textStyle: TextStyle = TextStyle.Default,
) : Presenter<TextPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = TextPresenter()
    }

    override fun safeMerge(other: TextPresenter) = TextPresenter(
        modifiers = modifiers.merge(other.modifiers),
        color = ComposeMerger.color.safeMerge(color, other.color),
        fontSize = ComposeMerger.textUnit.safeMerge(fontSize, other.fontSize),
        fontStyle = fontStyle.nullMerge(other.fontStyle),
        fontWeight = fontWeight.nullMerge(other.fontWeight),
        fontFamily = fontFamily.nullMerge(other.fontFamily),
        letterSpacing = ComposeMerger.textUnit.safeMerge(letterSpacing, other.letterSpacing),
        textDecoration = textDecoration.nullMerge(other.textDecoration),
        textAlign = textAlign.nullMerge(other.textAlign),
        lineHeight = ComposeMerger.textUnit.safeMerge(lineHeight, other.lineHeight),
        overflow = overflow.safeMerge(other.overflow, TextOverflow.Clip),
        softWrap = ComposeMerger.triState.safeMerge(softWrap, other.softWrap),
        maxLines = ComposeMerger.int.safeMerge(maxLines, other.maxLines),
        textStyle = textStyle.merge(other.textStyle),
    )

    @Composable
    override fun localized() = TextPresenter(
        color = Color.Unspecified,
        fontSize = TextUnit.Unspecified,
        fontStyle = null,
        fontWeight = null,
        fontFamily = null,
        letterSpacing = TextUnit.Unspecified,
        textDecoration = null,
        textAlign = null,
        lineHeight = TextUnit.Unspecified,
        overflow = TextOverflow.Clip,
        softWrap = TriState.TRUE,
        maxLines = Int.MAX_VALUE,
        textStyle = LocalTextStyle.current
    ).merge(this)
}