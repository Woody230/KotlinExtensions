package com.bselzer.ktx.compose.ui.layout.project

import androidx.compose.runtime.Composable

@Suppress("UNCHECKED_CAST")
abstract class Presenter<Model> : PresentationModel<Model> where Model : Presenter<Model> {
    override fun merge(other: Model?): Model = if (other == null || other === this) this as Model else safeMerge(other)
    protected abstract fun safeMerge(other: Model): Model

    @Composable
    override fun localized(): Model = merge(createLocalization())

    @Composable
    protected open fun createLocalization(): Model = this as Model
}