package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.bselzer.ktx.compose.ui.layout.project.Projector

class CardProjection(
    override val logic: CardLogic = CardLogic.Default,
    override val presentation: CardPresentation = CardPresentation.Default
) : Projector<CardLogic, CardPresentation>() {
    @Composable
    fun project(
        modifier: Modifier = Modifier,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        content: @Composable () -> Unit
    ) = contextualize {
        if (logic.onClick == null) {
            WithoutClick(modifier, content)
        } else {
            WithClick(modifier, interactionSource, logic.onClick, content)
        }
    }

    @Composable
    fun CardPresentation.WithoutClick(
        modifier: Modifier,
        content: @Composable () -> Unit
    ) = Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        content = content
    )

    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun CardPresentation.WithClick(
        modifier: Modifier,
        interactionSource: MutableInteractionSource,
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) = Card(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        backgroundColor = backgroundColor,
        contentColor = contentColor,
        border = border,
        elevation = elevation,
        interactionSource = interactionSource,
        indication = indication,
        enabled = logic.enabled,
        onClickLabel = logic.onClickLabel,
        role = role,
        content = content
    )
}