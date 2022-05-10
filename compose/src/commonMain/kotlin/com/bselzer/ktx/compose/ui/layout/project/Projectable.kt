package com.bselzer.ktx.compose.ui.layout.project

interface Projectable<Interactor, Presenter> where Interactor : Interactable, Presenter : Presentable<Presenter> {
    val interactor: Interactor
    val presenter: Presenter
}