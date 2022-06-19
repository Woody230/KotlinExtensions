package com.bselzer.ktx.compose.image.ui.layout.async

import androidx.compose.ui.graphics.painter.Painter

sealed interface AsyncImageResult {
    object Loading : AsyncImageResult
    object Failed : AsyncImageResult
    data class Success(val painter: Painter) : AsyncImageResult
}