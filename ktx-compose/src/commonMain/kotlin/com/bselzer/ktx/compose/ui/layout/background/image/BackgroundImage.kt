package com.bselzer.ktx.compose.ui.layout.background.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.bselzer.ktx.compose.ui.layout.image.ImageInteractor
import com.bselzer.ktx.compose.ui.layout.image.ImagePresenter
import com.bselzer.ktx.compose.ui.layout.image.ImageProjector
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.MatchParent

@Composable
fun BackgroundImage(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.TopStart,
    painter: Painter,
    presenter: ImagePresenter = backgroundImagePresenter(),
    content: @Composable BoxScope.() -> Unit
) = Box(
    modifier = modifier,
    contentAlignment = contentAlignment
) {
    ImageProjector(
        interactor = backgroundImageInteractor(painter = painter),
        presenter = ImagePresenter(modifier = MatchParent(this)) merge presenter
    ).Projection()

    content()
}

/**
 * Creates an [ImageInteractor] with the given [painter] and no content description.
 */
fun backgroundImageInteractor(painter: Painter) = ImageInteractor(
    painter = painter,
    contentDescription = null
)

@Composable
fun backgroundImagePresenter() = ImagePresenter(
    alignment = Alignment.Center,
    contentScale = ContentScale.Crop,
)