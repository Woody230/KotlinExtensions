package com.bselzer.ktx.compose.ui.layout.card

data class CardLogic(
    /**
     * Callback to be called when the card is clicked
     */
    val onClick: (() -> Unit)? = null,

    /**
     * Semantic / accessibility label for the onClick action
     */
    val onClickLabel: String? = null,

    /**
     * Controls the enabled state of the card. When false, this card will not be clickable
     */
    val enabled: Boolean = true,
) : LogicModel