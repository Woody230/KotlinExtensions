package com.bselzer.ktx.compose.ui.layout.scaffold

import androidx.compose.material.FabPosition
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.bselzer.ktx.compose.ui.layout.bottomappbar.BottomAppBarPresenter
import com.bselzer.ktx.compose.ui.layout.floatingactionbutton.FloatingActionButtonPresenter
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.modaldrawer.ModalDrawerPresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presentable
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.snackbarhost.SnackbarHostPresenter
import com.bselzer.ktx.compose.ui.layout.topappbar.TopAppBarPresenter
import com.bselzer.ktx.function.objects.safeMerge

data class ScaffoldPresenter(
    override val modifier: PresentableModifier = PresentableModifier,

    /**
     * The [Presentable] for the drawer.
     */
    val drawer: ModalDrawerPresenter = ModalDrawerPresenter.Default,

    /**
     * The [Presentable] for the snackbar host.
     */
    val snackbarHost: SnackbarHostPresenter = SnackbarHostPresenter.Default,

    /**
     * The [Presentable] for the top app bar.
     */
    val topBar: TopAppBarPresenter = TopAppBarPresenter.Default,

    /**
     * The [Presentable] for the bottom app bar.
     */
    val bottomBar: BottomAppBarPresenter = BottomAppBarPresenter.Default,

    /**
     * The [Presentable] for the floating action button.
     */
    val floatingActionButton: FloatingActionButtonPresenter = FloatingActionButtonPresenter.Default,

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
) : Presenter<ScaffoldPresenter>(modifier) {
    companion object {
        @Stable
        val Default = ScaffoldPresenter()
    }

    override fun safeMerge(other: ScaffoldPresenter) = ScaffoldPresenter(
        modifier = modifier.merge(other.modifier),
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
    override fun localized() = ScaffoldPresenter(
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