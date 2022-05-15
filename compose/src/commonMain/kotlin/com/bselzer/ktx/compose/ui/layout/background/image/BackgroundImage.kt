package com.bselzer.ktx.compose.ui.layout.background.image

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.MatchParent
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.ModularSize

fun backgroundImageInteractor(painter: Painter) = ImageInteractor(
    painter = painter,
    contentDescription = null
)

@Composable
fun backgroundImagePresenter() = ImagePresenter(
    modifier = ModularSize.FillSize,
) merge baseBackgroundImagePresenter()

@Composable
fun BoxScope.backgroundImagePresenter() = ImagePresenter(
    // Need to use matchParentSize() so that the image does not participate in sizing and can just fill the resulting size.
    modifier = MatchParent(this),
) merge baseBackgroundImagePresenter()

@Composable
private fun baseBackgroundImagePresenter() = ImagePresenter(
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
)