package com.bselzer.ktx.compose.ui.layout.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel

data class CardPresentation(
    /**
     * Defines the card's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     * The background color.
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * The preferred content color provided by this card to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this card.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * Border to draw on top of the card
     */
    val border: BorderStroke? = null,

    /**
     * The z-coordinate at which to place this card. This controls the size of the shadow below the card.
     */
    val elevation: Dp = 1.dp,

    /**
     *  The type of user interface element.
     *  Accessibility services might use this to describe the element or do customizations.
     *  For example, if the Surface acts as a button, you should pass the Role.Button
     */
    val role: Role? = null,

    /**
     * Indication to be shown when surface is pressed.
     * By default, indication from LocalIndication will be used. Pass null to show no indication, or current value from LocalIndication to show theme default
     */
    val indication: Indication? = ComposeMerger.indication.default
) : PresentationModel