package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable

/**
 * Represents a model for creating a composable.
 */
interface PresentationModel<Model> where Model : PresentationModel<Model> {
    /**
     * Returns a new presentation that is a combination of this presentation and the given [other] presentation.
     *
     * [other] presentation's null or inherit properties are replaced with the non-null properties of this text presentation.
     * Another way to think of it is that the "missing" properties of the [other] presentation are filled by the properties of this presentation
     *
     * If the given presentation is null, returns this text presentation.
     */
    infix fun merge(other: Model?): Model

    /**
     * Creates a localized version of this presentation.
     */
    @Composable
    fun localized(): Model
}