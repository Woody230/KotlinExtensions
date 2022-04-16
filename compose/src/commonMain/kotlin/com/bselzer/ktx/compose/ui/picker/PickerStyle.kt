package com.bselzer.ktx.compose.ui.picker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.compose.ui.style.*
import com.bselzer.ktx.compose.ui.style.StyleProvider.Companion.provider
import com.bselzer.ktx.function.objects.safeMerge

/**
 * The default picker animation offset.
 */
@Stable
val DefaultAnimationOffset = 18.dp

/**
 * CompositionLocal containing the preferred PickerStyle that will be used by picker components by default.
 */
val LocalPickerStyle: StyleProvider<PickerStyle> = compositionLocalOf { PickerStyle.Default }.provider()

data class PickerStyle(
    override val modifier: Modifier = Modifier,

    /**
     * The style of the container.
     */
    val style: ColumnStyle = ColumnStyle.Default,

    /**
     * The style of the text for displaying the value.
     */
    val textStyle: WordStyle = WordStyle.Default,

    /**
     * The style of the button wrapping the up and down icons.
     */
    val iconButtonStyle: IconButtonStyle = IconButtonStyle.Default,

    /**
     * The offset of the new value within the scrolling animation.
     */
    val animationOffset: Dp = DefaultAnimationOffset
) : ModifierStyle<PickerStyle>() {
    companion object {
        @Stable
        val Default = PickerStyle()
    }

    override fun modify(modifier: Modifier): PickerStyle = copy(modifier = modifier)

    override fun safeMerge(other: PickerStyle) = PickerStyle(
        modifier = modifier.then(other.modifier),
        style = style.with(other.style),
        textStyle = textStyle.with(other.textStyle),
        iconButtonStyle = iconButtonStyle.with(other.iconButtonStyle),
        animationOffset = animationOffset.safeMerge(other.animationOffset, DefaultAnimationOffset)
    )

    @Composable
    override fun localized() = PickerStyle(
        style = LocalColumnStyle.current,
        textStyle = LocalWordStyle.current,
        iconButtonStyle = LocalIconButtonStyle.current
    ).with(this)
}