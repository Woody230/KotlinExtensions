package com.bselzer.ktx.compose.ui.graphics.color

import androidx.compose.material.Colors
import androidx.compose.ui.graphics.Color

/**
 * @return the hyperlink color
 */
fun Colors.hyperlink() = if (isLight) Color(6, 69, 173) else Color(0, 255, 255)