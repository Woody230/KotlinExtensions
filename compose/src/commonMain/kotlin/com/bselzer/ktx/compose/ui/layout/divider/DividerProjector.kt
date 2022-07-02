package com.bselzer.ktx.compose.ui.layout.divider

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.LayoutOrientation
import com.bselzer.ktx.compose.ui.layout.project.Projector

class DividerProjector(
    interactor: DividerInteractor = DividerInteractor.Default,
    presenter: DividerPresenter = DividerPresenter.Default
) : Projector<DividerInteractor, DividerPresenter>(interactor, presenter) {
    @Composable
    fun Projection(
        modifier: Modifier = Modifier
    ) = contextualize(modifier) { combinedModifier, interactor, presenter ->
        when (presenter.orientation) {
            LayoutOrientation.VERTICAL -> presenter.VerticalDivider(combinedModifier)
            else -> presenter.HorizontalDivider(combinedModifier)
        }
    }

    @Composable
    private fun DividerPresenter.VerticalDivider(
        modifier: Modifier
    ) {
        val indentMod = if (startIndent.value != 0f) {
            Modifier.padding(start = startIndent)
        } else {
            Modifier
        }
        val targetThickness = if (thickness == Dp.Hairline) {
            (1f / LocalDensity.current.density).dp
        } else {
            thickness
        }
        Box(
            modifier.then(indentMod)
                .fillMaxHeight()
                .width(targetThickness)
                .background(color = color)
        )
    }

    @Composable
    private fun DividerPresenter.HorizontalDivider(
        modifier: Modifier
    ) = Divider(
        modifier = modifier,
        color = color,
        thickness = thickness,
        startIndent = startIndent
    )
}