package com.bselzer.ktx.compose.ui.layout.image.async

import androidx.compose.ui.graphics.painter.Painter

sealed interface AsyncImageResult {
    data object Loading : AsyncImageResult
    data object Failed : AsyncImageResult
    data class Success(val painter: Painter) : AsyncImageResult
}