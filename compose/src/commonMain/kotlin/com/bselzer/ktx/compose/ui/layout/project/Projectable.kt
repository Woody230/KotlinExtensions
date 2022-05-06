package com.bselzer.ktx.compose.ui.layout.project

interface Projectable<Logic, Presentation> where Logic : LogicModel, Presentation : PresentationModel<Presentation> {
    val logic: Logic
    val presentation: Presentation
}