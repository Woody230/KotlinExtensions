package com.bselzer.ktx.compose.ui.layout.appbar.bottom

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
    interactor: BottomAppBarInteractor,
    presenter: BottomAppBarPresenter = BottomAppBarPresenter.Default
) : Projector<BottomAppBarInteractor, BottomAppBarPresenter>(interactor, presenter) {
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
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        BottomAppBar(
            modifier = combinedModifier,
            backgroundColor = presenter.backgroundColor,
            contentColor = presenter.contentColor,
            elevation = presenter.elevation,
            cutoutShape = presenter.cutoutShape,
            contentPadding = presenter.contentPadding
        ) {
            val navigationProjector = interactor.navigation?.let { navigation -> IconButtonProjector(navigation, presenter.icon) }
            val actionProjector = interactor.actions.map { action -> IconButtonProjector(action, presenter.icon) }
            if (navigationProjector == null) {
                Spacer(TitleInsetWithoutIcon)
            } else {
                Row(TitleIconModifier, verticalAlignment = Alignment.CenterVertically) {
                    CompositionLocalProvider(
                        LocalContentAlpha provides ContentAlpha.high,
                        content = { navigationProjector.Projection() }
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
                    content = { actionProjector.forEach { action -> action.Projection() } }
                )
            }
        }
    }
}