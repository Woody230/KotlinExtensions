package com.bselzer.ktx.compose.ui.text

import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*

/**
 * Appends the [hypertext] with a hyperlinked [style].
 *
 * @param hypertext the text representing the hyperlink
 * @param hyperlink the hyperlink
 * @param style the style of the [hypertext] to convert to a hyperlink format
 * @param listener interaction listener triggered when user interacts with this link.
 *      When clicking on the text to which this annotation
 *      is attached, the app will try to open the url using [androidx.compose.ui.platform.UriHandler].
 *      However, if [listener] is provided, its [listener.onClick]
 *      method will be called instead and so you need to then handle opening url manually (for
 *      example by calling [androidx.compose.ui.platform.UriHandler]).
 */
@Composable
fun AnnotatedString.Builder.withHyperlink(
    hypertext: String,
    hyperlink: String = hypertext,
    style: TextStyle = LocalTextStyle.current,
    listener: HyperlinkListener? = null
) {
    val annotation = LinkAnnotation.Url(
        url = hyperlink,
        styles = TextLinkStyles(
            style = style.toSpanStyle().hyperlink()
        ),
        linkInteractionListener = when (listener) {
            null -> null
            else -> {
                { listener.onClick(hyperlink) }
            }
        }
    )

    withLink(annotation) {
        append(hypertext)
    }
}

/**
 * Appends the [text] with the [style] in the given [color].
 *
 * @param text the text
 * @param color the color of the [text]
 * @param style the style of the [text]
 */
@Composable
fun AnnotatedString.Builder.withColor(text: String, color: Color, style: TextStyle = LocalTextStyle.current) = withStyle(style = style.toSpanStyle().copy(color = color)) {
    append(text)
}

/**
 * Applies a span style using the current [LocalTextStyle].
 *
 * @param block the block to apply the style to
 * @param R the result of the [block]
 * @return the result of the [block]
 */
@Composable
inline fun <R : Any> AnnotatedString.Builder.withSpanStyle(crossinline block: AnnotatedString.Builder.() -> R): R =
    withStyle(style = LocalTextStyle.current.toSpanStyle(), block)

/**
 * Applies a paragraph style using the current [LocalTextStyle].
 *
 * @param block the block to apply the style to
 * @param R the result of the [block]
 * @return the result of the [block]
 */
@Composable
inline fun <R : Any> AnnotatedString.Builder.withParagraphStyle(crossinline block: AnnotatedString.Builder.() -> R): R =
    withStyle(style = LocalTextStyle.current.toParagraphStyle(), block)