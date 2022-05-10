package com.bselzer.ktx.compose.ui.layout.centeredtext

import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.spacer.SpacerInteractor
import com.bselzer.ktx.compose.ui.layout.text.TextInteractor

data class CenteredTextInteractor(
    /**
     * The [Interactor] of the starting text.
     */
    val start: TextInteractor,

    /**
     * The [Interactor] of the ending text.
     */
    val end: TextInteractor,

    /**
     * The [Interactor] of the spacing between the starting and ending text.
     */
    val spacer: SpacerInteractor = SpacerInteractor.Default
) : Interactor()