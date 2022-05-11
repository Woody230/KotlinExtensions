package com.bselzer.ktx.compose.ui.layout.preference.duration

import com.bselzer.ktx.compose.ui.layout.icon.IconInteractor
import com.bselzer.ktx.compose.ui.layout.modifier.InteractableModifiers
import com.bselzer.ktx.compose.ui.layout.preference.alertdialog.AlertDialogPreferenceInteractor
import com.bselzer.ktx.compose.ui.layout.project.Interactor
import com.bselzer.ktx.function.objects.userFriendly
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days
import kotlin.time.DurationUnit
import kotlin.time.toDuration

data class DurationPreferenceInteractor(
    override val modifiers: InteractableModifiers = InteractableModifiers.Default,
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
     * The initial amount of the [initialUnit].
     */
    val initialAmount: Int = 1,

    /**
     * The type of [Duration] component of [initialAmount].
     */
    val initialUnit: DurationUnit = DurationUnit.DAYS,

    /**
     * The minimum duration bound.
     */
    val minimum: Duration = 0.days,

    /**
     * The maximum duration bound.
     */
    val maximum: Duration = Int.MAX_VALUE.days,

    /**
     * The selectable [DurationUnit] types.
     */
    val units: List<DurationUnit> = DurationUnit.values().toList(),

    /**
     * Converts the [DurationUnit] into a displayable label.
     */
    val getLabel: (DurationUnit) -> String = { it.userFriendly() }
) : Interactor(modifiers) {
    /**
     * The [Duration] formed from the [initialAmount] and [initialUnit].
     */
    val initialDuration: Duration = initialAmount.toDuration(initialUnit)
}