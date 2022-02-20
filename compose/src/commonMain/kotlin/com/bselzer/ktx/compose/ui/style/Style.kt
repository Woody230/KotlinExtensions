package com.bselzer.ktx.compose.ui.style

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier

interface Style

interface ModifiableStyle {
    /**
     * Modifier to apply to the layout node.
     */
    val modifier: Modifier
}

/**
 * Throws an exception indicating that the style was not initialized.
 */
internal inline fun <reified T> styleNotInitialized(): T = throw NotImplementedError("Style '${T::class.qualifiedName}' not initialized. Use ProvideLocalizedStyles().")

/**
 * Initializes the styles to their localized state.
 *
 * This should be called after initializing a material theme.
 */
@Composable
fun ProvideLocalizedStyles(content: @Composable () -> Unit) = CompositionLocalProvider(
    LocalBadgeStyle provides badgeStyle(),
    LocalBoxStyle provides boxStyle(),
    LocalButtonStyle provides buttonStyle(),
    LocalCardStyle provides cardStyle(),
    LocalClickableCardStyle provides clickableCardStyle(),
    LocalColumnStyle provides columnStyle(),
    LocalDividerStyle provides dividerStyle(),
    LocalIconButtonStyle provides iconButtonStyle(),
    LocalIconStyle provides iconStyle(),
    LocalImageStyle provides imageStyle(),
    LocalLazyColumnStyle provides lazyColumnStyle(),
    LocalLazyRowStyle provides lazyRowStyle(),
    LocalRadioButtonStyle provides radioButtonStyle(),
    LocalRowStyle provides rowStyle(),
    LocalSurfaceStyle provides surfaceStyle(),
    LocalClickableSurfaceStyle provides clickableSurfaceStyle(),
    LocalWordStyle provides wordStyle(),
    content = content
)