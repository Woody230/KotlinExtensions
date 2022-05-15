package com.bselzer.ktx.compose.ui.layout.modifier

import androidx.compose.ui.Modifier

interface Modifiable {
    /**
     * The modifier associated with the [Modifiable] properties.
     */
    val modifier: Modifier
}