package com.bselzer.ktx.resource.images

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun ImageVector.painter() = rememberVectorPainter(this)