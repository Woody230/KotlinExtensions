package com.bselzer.library.kotlin.extension.compose.ui.style

import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle

/**
 * @return a copy of the [SpanStyle] with a hyperlink color and an underline
 */
@Composable
fun SpanStyle.hyperlink() = copy(color = MaterialTheme.colors.hyperlink(), textDecoration = TextDecoration.Underline)

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