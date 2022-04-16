package com.bselzer.ktx.compose.runtime

import androidx.compose.runtime.Composable

/**
 * Converts the [Unit] into an empty [Composable].
 */
fun <Receiver> Unit.composable(): @Composable Receiver.() -> Unit = {}