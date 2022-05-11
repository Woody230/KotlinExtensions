package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarInteractor
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonInteractor
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostInteractor
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarInteractor

data class ScaffoldInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,

    /**
     * The [Interactor] for the drawer.
     */
    val drawer: ModalDrawerInteractor? = null,

    /**
     * The [Interactor] for the snackbar host.
     */
    val snackbarHost: SnackbarHostInteractor = SnackbarHostInteractor.Default,

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
) : Interactor(modifiers) {
    val state: ScaffoldState = ScaffoldState(drawer?.state ?: ModalDrawerInteractor.Default.state, snackbarHost.state)

    companion object {
        @Stable
        val Default = ScaffoldInteractor()
    }
}