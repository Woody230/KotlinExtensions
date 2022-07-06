package com.bselzer.ktx.compose.ui.layout.progress.indicator

import com.bselzer.ktx.compose.ui.layout.project.Presentable

sealed interface ProgressIndicatorPresenter<Model> : Presentable<Model> where Model : ProgressIndicatorPresenter<Model>

/**
 * Returns a new presentation that is a combination of this presentation and the given [other] presentation if they are of the same type.
 * Otherwise, the [other] presentation is used.
 */
fun ProgressIndicatorPresenter<*>.merge(other: ProgressIndicatorPresenter<*>) = when {
    // If they are the same type of progress indicator, then merge them normally.
    this is CircularIndicatorPresenter && other is CircularIndicatorPresenter -> merge(other)
    this is LinearIndicatorPresenter && other is LinearIndicatorPresenter -> merge(other)

    // Otherwise the other presenter takes precedence.
    else -> other
}
