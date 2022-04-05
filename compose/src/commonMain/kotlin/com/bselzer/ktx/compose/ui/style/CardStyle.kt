package com.bselzer.ktx.compose.ui.style

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Indication
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bselzer.ktx.function.objects.nullMerge
import com.bselzer.ktx.function.objects.safeMerge

/**
 * CompositionLocal containing the preferred CardStyle that will be used by Card components by default.
 */
val LocalCardStyle: StyleProvider<CardStyle> = StyleProvider(compositionLocalOf { CardStyle.Default })

/**
 * CompositionLocal containing the preferred ClickableCardStyle that will be used by clickable Card components by default.
 */
val LocalClickableCardStyle: StyleProvider<ClickableCardStyle> = StyleProvider(compositionLocalOf { ClickableCardStyle.Default })

/**
 * A wrapper around the standard [Card] composable.
 *
 * @param style the style describing how to lay out the Card
 * @param content the content to lay out inside the Card
 */
@Composable
fun Card(
    style: CardStyle = LocalCardStyle.current,
    content: @Composable () -> Unit,
) = Card(
    modifier = style.modifier,
    shape = style.shape,
    backgroundColor = style.backgroundColor,
    contentColor = style.contentColor,
    border = style.border,
    elevation = style.elevation,
    content = content
)

/**
 * A wrapper around the standard [Card] composable.
 *
 * @param onClick callback to be called when the card is clicked
 * @param onClickLabel semantic / accessibility label for the onClick action
 * @param interactionSource the MutableInteractionSource representing the stream of Interactions for this Card.
 * You can create and pass in your own remembered MutableInteractionSource if you want to observe Interactions and customize the appearance / behavior of this card in different Interactions.indication - indication to be shown when card is pressed.
 * By default, indication from LocalIndication will be used. Pass null to show no indication, or current value from LocalIndication to show theme default
 * @param style the style describing how to lay out the Card
 * @param content the content to lay out inside the Card
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Card(
    onClick: () -> Unit,
    onClickLabel: String? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    style: ClickableCardStyle = LocalClickableCardStyle.current,
    content: @Composable () -> Unit,
) = Card(
    onClick = onClick,
    modifier = style.modifier,
    shape = style.shape,
    backgroundColor = style.backgroundColor,
    contentColor = style.contentColor,
    border = style.border,
    elevation = style.elevation,
    interactionSource = interactionSource,
    indication = style.indication,
    enabled = style.enabled,
    onClickLabel = onClickLabel,
    role = style.role,
    content = content
)

/**
* The style arguments associated with a [Card] composable.
*/
data class CardStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Defines the card's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = DefaultShape,

    /**
     * The background color.
     */
    val backgroundColor: Color = Color.Unspecified,

    /**
     * The preferred content color provided by this card to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this card.
     */
    val contentColor: Color = Color.Unspecified,

    /**
     * Border to draw on top of the card
     */
    val border: BorderStroke? = null,

    /**
     * The z-coordinate at which to place this card. This controls the size of the shadow below the card.
     */
    val elevation: Dp = 1.dp,
) : ModifiableStyle<CardStyle> {
    companion object {
        @Stable
        val Default = CardStyle()
    }

    override fun merge(other: CardStyle?): CardStyle = if (other == null) this else CardStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        backgroundColor = backgroundColor.merge(other.backgroundColor),
        contentColor = contentColor.merge(other.contentColor),
        border = border.nullMerge(other.border),
        elevation = elevation.safeMerge(other.elevation, 1.dp),
    )

    @Composable
    override fun localized(): CardStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        CardStyle(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor),
        ).merge(this)
    }
}


/**
 * The style arguments associated with a [Card] composable that is clickable.
 */
data class ClickableCardStyle(
    override val modifier: Modifier = Modifier,

    /**
     * Defines the card's shape as well its shadow. A shadow is only displayed if the elevation is greater than zero.
     */
    val shape: Shape = DefaultShape,

    /**
     * The background color.
     */
    val backgroundColor: Color = Color.Unspecified,

    /**
     * The preferred content color provided by this card to its children. Defaults to either the matching content color for backgroundColor, or if backgroundColor is not a color from the theme, this will keep the same value set above this card.
     */
    val contentColor: Color = Color.Unspecified,

    /**
     * Border to draw on top of the card
     */
    val border: BorderStroke? = null,

    /**
     * The z-coordinate at which to place this card. This controls the size of the shadow below the card.
     */
    val elevation: Dp = 1.dp,

    /**
     * indication to be shown when surface is pressed.
     * By default, indication from LocalIndication will be used. Pass null to show no indication, or current value from LocalIndication to show theme default
     */
    val indication: Indication? = null,

    /**
     * Controls the enabled state of the card. When false, this card will not be clickable
     */
    val enabled: Boolean = true,

    /**
     *  The type of user interface element.
     *  Accessibility services might use this to describe the element or do customizations.
     *  For example, if the Surface acts as a button, you should pass the Role.Button
     */
    val role: Role? = null,
) : ModifiableStyle<ClickableCardStyle> {
    companion object {
        @Stable
        val Default = ClickableCardStyle()
    }

    override fun merge(other: ClickableCardStyle?): ClickableCardStyle = if (other == null) this else ClickableCardStyle(
        modifier = modifier.then(other.modifier),
        shape = shape.merge(other.shape),
        backgroundColor = backgroundColor.merge(other.backgroundColor),
        contentColor = contentColor.merge(other.contentColor),
        border = border.nullMerge(other.border),
        elevation = elevation.safeMerge(other.elevation, 1.dp),
        indication = indication.nullMerge(other.indication),
        enabled = enabled.safeMerge(other.enabled, true),
        role = role.nullMerge(other.role)
    )

    @Composable
    override fun localized(): ClickableCardStyle = run {
        val backgroundColor = MaterialTheme.colors.surface
        ClickableCardStyle(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = backgroundColor,
            contentColor = contentColorFor(backgroundColor = backgroundColor),
            indication = LocalIndication.current
        ).merge(this)
    }
}