package com.bselzer.ktx.compose.ui.layout.bottomappbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomAppBar
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.iconbutton.IconButtonProjector
import com.bselzer.ktx.compose.ui.layout.project.Projector

class BottomAppBarProjector(
    override val interactor: BottomAppBarInteractor,
    override val presenter: BottomAppBarPresenter = BottomAppBarPresenter.Default
) : Projector<BottomAppBarInteractor, BottomAppBarPresenter>() {
    private val navigationProjection = interactor.navigation?.let { navigation -> IconButtonProjector(navigation, presenter.icon) }
    private val actionProjection = interactor.actions.map { action -> IconButtonProjector(action, presenter.icon) }

    companion object {
        private val AppBarHorizontalPadding = 4.dp

        // Start inset for the title when there is no navigation icon provided
        private val TitleInsetWithoutIcon = Modifier.width(16.dp - AppBarHorizontalPadding)

        // Start inset for the title when there is a navigation icon provided
        private val TitleIconModifier = Modifier
            .fillMaxHeight()
            .width(72.dp - AppBarHorizontalPadding)
    }

    @Composable
    fun project(
        modifier: Modifier = Modifier
    ) = contextualize {
        BottomAppBar(
            modifier = modifier,
            backgroundColor = backgroundColor,
            contentColor = contentColor,
            elevation = elevation,
            cutoutShape = cutoutShape,
            contentPadding = contentPadding
        ) {
            if (navigationProjection == null) {
                Spacer(TitleInsetWithoutIcon)
            } else {
                Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        content = { navigationProjection.project() }
                    )
                }
            }

            Spacer(modifier = Modifier
                .fillMaxHeight()
                .weight(1f))

            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    Modifier.fillMaxHeight(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                    content = { actionProjection.forEach { action -> action.project() } }
                )
            }
        }
    }
}