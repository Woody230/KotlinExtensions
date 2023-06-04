package com.bselzer.ktx.compose.ui.layout.preference.duration

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.interactable.InteractableModifier
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.function.objects.userFriendly
import kotlin.time.Duration
import kotlin.time.DurationUnit

data class DurationPreferenceInteractor(
    override val modifier: InteractableModifier = InteractableModifier,
    val preference: AlertDialogPreferenceInteractor,

    /**
     * The [Interactor] for the up-directional icon.
     */
    val upIcon: IconInteractor,

    /**
     * The [Interactor] for the down-directional icon.
     */
    val downIcon: IconInteractor,

    /**
     * The amount of the [unit].
     */
    val amount: Int,

    /**
     * The type of [Duration] component of [amount].
     */
    val unit: DurationUnit,

    /**
     * Callback to perform when the value changes with the new amount and unit.
     */
    val onValueChange: (Int, DurationUnit) -> Unit,

    /**
     * The range of amounts to select from.
     */
    val amountRange: IntRange = 1..Int.MAX_VALUE,

    // TODO enum entries https://kotlinlang.org/docs/whatsnew1820.html#a-modern-and-performant-replacement-of-the-enum-class-values-function
    /**
     * The selectable [DurationUnit] types.
     */
    val units: List<DurationUnit> = DurationUnit.values().toList(),

    /**
     * Converts the [DurationUnit] into a displayable label.
     */
    val unitLabel: (DurationUnit) -> String = { it.userFriendly() }
) : Interactor(modifier)