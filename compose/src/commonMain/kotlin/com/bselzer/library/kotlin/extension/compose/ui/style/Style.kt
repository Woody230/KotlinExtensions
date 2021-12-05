package com.bselzer.library.kotlin.extension.compose.ui.style

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

/**
 * @return a copy of the [SpanStyle] with a hyperlink color and an underline
 */
@Composable
fun SpanStyle.hyperlink() = copy(color = MaterialTheme.colors.hyperlink(), textDecoration = TextDecoration.Underline)

/**
 * Appends the [text] with a hyperlinked [style] with a [tag] annotation.
 *
 * @param text the hyperlink text
 * @param tag the annotation tag
 * @param hyperlink the hyperlink as an annotation
 * @param style the style of the [text] to convert to a hyperlink format
 */
@Composable
fun AnnotatedString.Builder.hyperlink(text: String, tag: String, hyperlink: String = text, style: TextStyle = LocalTextStyle.current) =
    withStyle(style = style.toSpanStyle().hyperlink()) {
        pushStringAnnotation(tag, hyperlink)
        append(text)
        pop()
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