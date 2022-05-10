package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.material.ScaffoldState
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarInteractor
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostInteractor
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarInteractor

data class ScaffoldInteractor(
    /**
     * The [Interactor] for the drawer.
     */
    val drawer: ModalDrawerInteractor,

    /**
     * The [Interactor] for the snackbar host.
     */
    val snackbarHost: SnackbarHostInteractor,

    /**
     * The [Interactor] for the top app bar.
     */
    val topBar: TopAppBarInteractor? = null,

    /**
     * The [Interactor] for the bottom app bar.
     */
    val bottomBar: BottomAppBarInteractor? = null,

    /**
     * The [Interactor] for the floating action button.
     */
    val floatingActionButton: FloatingActionButtonInteractor? = null,
) : Interactor() {
    val state: ScaffoldState = ScaffoldState(drawer.state, snackbarHost.state)
}