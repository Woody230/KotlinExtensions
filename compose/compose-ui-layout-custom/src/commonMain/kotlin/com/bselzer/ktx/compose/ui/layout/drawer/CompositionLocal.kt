package com.bselzer.ktx.compose.ui.layout.drawer

import androidx.compose.material.DrawerState
import androidx.compose.material.DrawerValue
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf

/**
 * CompositionLocal containing the [DrawerState].
 */
val LocalDrawerState: ProvidableCompositionLocal<DrawerState> = compositionLocalOf { DrawerState(initialValue = DrawerValue.Closed) }