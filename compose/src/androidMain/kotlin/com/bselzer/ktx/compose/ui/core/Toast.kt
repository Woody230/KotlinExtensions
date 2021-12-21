package com.bselzer.ktx.compose.ui.core

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Shows the toast with the [text] for a [duration] amount of time using the local context.
 */
@Composable
fun ShowToast(text: CharSequence, duration: Int, block: Toast.() -> Unit = {}) = Toast.makeText(LocalContext.current, text, duration).apply(block).show()