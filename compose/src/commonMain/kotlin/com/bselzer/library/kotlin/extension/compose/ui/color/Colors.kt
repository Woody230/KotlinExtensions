package com.bselzer.library.kotlin.extension.compose.ui.color

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * @return the hyperlink color
 */
fun Colors.hyperlink() = if (isLight) Color(6, 69, 173) else Color(0, 255, 255)

/**
 * @return a copy of the [SpanStyle] with a hyperlink color and an underline
 */
@Composable
fun SpanStyle.hyperlink() = copy(color = MaterialTheme.colors.hyperlink(), textDecoration = TextDecoration.Underline)