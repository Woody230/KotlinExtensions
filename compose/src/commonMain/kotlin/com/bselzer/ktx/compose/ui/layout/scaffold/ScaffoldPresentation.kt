package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarPresentation
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonPresentation
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerPresentation
import com.bselzer.ktx.compose.ui.layout.project.PresentationModel
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostPresentation
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarPresentation
import com.bselzer.ktx.function.objects.safeMerge

data class ScaffoldPresentation(
    /**
     * The [PresentationModel] for the drawer.
     */
    val drawer: ModalDrawerPresentation = ModalDrawerPresentation.Default,

    /**
     * The [PresentationModel] for the snackbar host.
     */
    val snackbarHost: SnackbarHostPresentation = SnackbarHostPresentation.Default,

    /**
     * The [PresentationModel] for the top app bar.
     */
    val topBar: TopAppBarPresentation = TopAppBarPresentation.Default,

    /**
     * The [PresentationModel] for the bottom app bar.
     */
    val bottomBar: BottomAppBarPresentation = BottomAppBarPresentation.Default,

    /**
     * The [PresentationModel] for the floating action button.
     */
    val floatingActionButton: FloatingActionButtonPresentation = FloatingActionButtonPresentation.Default,

    /**
     * Position of the FAB on the screen. See FabPosition for possible options available.
     */
    val floatingActionButtonPosition: FabPosition = FabPosition.End,

    /**
     * Whether floatingActionButton should overlap with bottomBar for half a height, if bottomBar exists.
     * Ignored if there's no bottomBar or no floatingActionButton.
     */
    val isFloatingActionButtonDocked: TriState = TriState.DEFAULT,

    /**
     * Background of the scaffold body
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * Color of the content in scaffold body.
     * Defaults to either the matching content color for backgroundColor, or, if it is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = ComposeMerger.color.default
) : Presenter<ScaffoldPresentation>() {
    companion object {
        @Stable
        val Default = ScaffoldPresentation()
    }

    override fun safeMerge(other: ScaffoldPresentation) = ScaffoldPresentation(
        drawer = drawer.merge(other.drawer),
        snackbarHost = snackbarHost.merge(other.snackbarHost),
        topBar = topBar.merge(other.topBar),
        bottomBar = bottomBar.merge(other.bottomBar),
        floatingActionButton = floatingActionButton.merge(other.floatingActionButton),
        floatingActionButtonPosition = floatingActionButtonPosition.safeMerge(other.floatingActionButtonPosition, FabPosition.End),
        isFloatingActionButtonDocked = ComposeMerger.triState.safeMerge(isFloatingActionButtonDocked, other.isFloatingActionButtonDocked),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor)
    )

    @Composable
    override fun localized() = ScaffoldPresentation(
        backgroundColor = MaterialTheme.colors.background,
        isFloatingActionButtonDocked = TriState.FALSE,
    ).merge(this).run {
        copy(
            drawer = drawer.localized(),
            snackbarHost = snackbarHost.localized(),
            topBar = topBar.localized(),
            bottomBar = bottomBar.localized(),
            floatingActionButton = floatingActionButton.localized(),
            contentColor = if (ComposeMerger.color.isDefault(contentColor)) contentColorFor(backgroundColor) else contentColor
        )
    }
}