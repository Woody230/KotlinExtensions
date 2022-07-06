package com.bselzer.ktx.compose.ui.layout.project

interface Projectable<Interactor, Presenter> where Interactor : Interactable, Presenter : Presentable<Presenter>