package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.Projectable

class CardProjection(
    override val logic: CardLogic = CardLogic(),
    override val presentation: CardPresentation = CardPresentation()
) : Projectable<CardLogic, CardPresentation> {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) {
        val shape = ComposeMerger.shape.safeTake(presentation.shape, MaterialTheme.shapes.medium)
        val backgroundColor = ComposeMerger.color.safeTake(presentation.backgroundColor, MaterialTheme.colors.surface)
        val contentColor = ComposeMerger.color.safeTake(presentation.contentColor, contentColorFor(backgroundColor))

        if (logic.onClick == null) {
            WithoutClick(modifier, shape, backgroundColor, contentColor, content)
        } else {
            WithClick(modifier, interactionSource, logic.onClick, shape, backgroundColor, contentColor, content)
        }
    }

    @Composable
    fun WithoutClick(
        modifier: Modifier,
        shape: Shape,
        backgroundColor: Color,
        contentColor: Color,
        content: @Composable () -> Unit
    ) = Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = presentation.border,
        elevation = presentation.elevation,
        content = content
    )

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun WithClick(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        shape: Shape,
        backgroundColor: Color,
        contentColor: Color,
        content: @Composable () -> Unit
    ) = Card(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = presentation.border,
        elevation = presentation.elevation,
        interactionSource = interactionSource,
        indication = presentation.indication,
        enabled = logic.enabled,
        onClickLabel = logic.onClickLabel,
        role = presentation.role,
        content = content
    )
}