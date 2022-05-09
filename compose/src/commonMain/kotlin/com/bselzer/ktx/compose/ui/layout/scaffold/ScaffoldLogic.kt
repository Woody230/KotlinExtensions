package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.material.ScaffoldState
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarLogic
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonLogic
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerLogic
import com.bselzer.ktx.compose.ui.layout.project.LogicModel
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostLogic
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarLogic

data class ScaffoldLogic(
    /**
     * The [LogicModel] for the drawer.
     */
    val drawer: ModalDrawerLogic,

    /**
     * The [LogicModel] for the snackbar host.
     */
    val snackbarHost: SnackbarHostLogic,

    /**
     * The [LogicModel] for the top app bar.
     */
    val topBar: TopAppBarLogic? = null,

    /**
     * The [LogicModel] for the bottom app bar.
     */
    val bottomBar: BottomAppBarLogic? = null,

    /**
     * The [LogicModel] for the floating action button.
     */
    val floatingActionButton: FloatingActionButtonLogic? = null,
) : LogicModel {
    val state: ScaffoldState = ScaffoldState(drawer.state, snackbarHost.state)
}