package com.bselzer.ktx.compose.ui.layout.spacer

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.project.Projector
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerDirection.HORIZONTAL
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerDirection.VERTICAL

class SpacerProjector(
    override val interactor: SpacerInteractor,
    override val presenter: SpacerPresenter = SpacerPresenter.Default
) : Projector<SpacerInteractor, SpacerPresenter>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        direction: SpacerDirection
    ) = contextualize {
        val width = when (direction) {
            VERTICAL -> Modifier.width(0.dp)
            HORIZONTAL -> Modifier.width(size)
        }

        val height = when (direction) {
            VERTICAL -> Modifier.height(size)
            HORIZONTAL -> Modifier.height(0.dp)
        }

        Spacer(
            modifier = modifier
                .then(width)
                .then(height)
        )
    }
}