package com.bselzer.ktx.compose.ui.layout.snackbar

import androidx.compose.material.MaterialTheme
import androidx.compose.material.SnackbarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.layout.merge.ComposeMerger
import com.bselzer.ktx.compose.ui.layout.merge.TriState
import com.bselzer.ktx.compose.ui.layout.project.Presenter

data class SnackbarPresentation(
    /**
     * Whether or not action should be put on the separate line. Recommended for action with long action text
     */
    val actionOnNewLine: TriState = ComposeMerger.triState.default,

    /**
     * Defines the Snackbar's shape as well as its shadow
     */
    val shape: Shape = ComposeMerger.shape.default,

    /**
     *  Background color of the Snackbar
     */
    val backgroundColor: Color = ComposeMerger.color.default,

    /**
     * Color of the content to use inside the snackbar.
     * Defaults to either the matching content color for backgroundColor, or, if it is not a color from the theme, this will keep the same value set above this Surface.
     */
    val contentColor: Color = ComposeMerger.color.default,

    /**
     * Color of the action.
     */
    val actionColor: Color = ComposeMerger.color.default,

    /**
     * The z-coordinate at which to place the SnackBar. This controls the size of the shadow below the SnackBar
     */
    val elevation: Dp = ComposeMerger.dp.default
) : Presenter<SnackbarPresentation>() {
    companion object {
        @Stable
        val Default = SnackbarPresentation()
    }

    override fun safeMerge(other: SnackbarPresentation) = SnackbarPresentation(
        actionOnNewLine = ComposeMerger.triState.safeMerge(actionOnNewLine, other.actionOnNewLine),
        shape = ComposeMerger.shape.safeMerge(shape, other.shape),
        backgroundColor = ComposeMerger.color.safeMerge(backgroundColor, other.backgroundColor),
        contentColor = ComposeMerger.color.safeMerge(contentColor, other.contentColor),
        actionColor = ComposeMerger.color.safeMerge(actionColor, other.actionColor),
        elevation = ComposeMerger.dp.safeMerge(elevation, other.elevation)
    )

    @Composable
    override fun localized() = SnackbarPresentation(
        actionOnNewLine = TriState.FALSE,
        shape = MaterialTheme.shapes.small,
        backgroundColor = SnackbarDefaults.backgroundColor,
        contentColor = MaterialTheme.colors.surface,
        actionColor = SnackbarDefaults.primaryActionColor,
        elevation = 6.dp
    ).merge(this)
}