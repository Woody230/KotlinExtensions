package com.bselzer.ktx.coroutine

import android.content.Context
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Shows a Toast using the main coroutine context.
 */
suspend fun CoroutineScope.showToast(context: Context, text: CharSequence, duration: Int) = withContext(Dispatchers.Main) {
    Toast.makeText(context, text, duration).show()
}