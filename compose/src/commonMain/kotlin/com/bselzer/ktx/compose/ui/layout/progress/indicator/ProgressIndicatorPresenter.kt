package com.bselzer.ktx.compose.ui.layout.progress.indicator

import com.bselzer.ktx.compose.ui.layout.project.Presentable

sealed interface ProgressIndicatorPresenter<Model> : Presentable<Model> where Model : ProgressIndicatorPresenter<Model>
