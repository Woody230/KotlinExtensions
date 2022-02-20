package com.bselzer.ktx.compose.ui.style

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration

/**
 * @return a copy of the [SpanStyle] with a hyperlink color and an underline
 */
@Composable
fun SpanStyle.hyperlink() = copy(color = MaterialTheme.colors.hyperlink(), textDecoration = TextDecoration.Underline)
