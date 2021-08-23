package com.bselzer.library.kotlin.extension.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

/**
 * Launch an effect that persists to the lifecycle of the calling method.
 *
 * Warning: LaunchedEffect(true) is as suspicious as a while(true). Even though there are valid use cases for it, always pause and make sure that's what you need.
 * https://developer.android.com/jetpack/compose/side-effects?hl=da#rememberupdatedstate
 */
@Composable
fun LaunchPersistentEffect(context: CoroutineContext = Dispatchers.IO, block: suspend CoroutineScope.() -> Unit) = LaunchedEffect(true) {
    withContext(context)
    {
        block()
    }
}