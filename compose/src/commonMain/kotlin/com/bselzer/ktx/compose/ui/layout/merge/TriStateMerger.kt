package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable

class TriStateMerger : ComponentMerger<TriState> {
    override val default: TriState = Default

    companion object {
        @Stable
        val Default = TriState.DEFAULT
    }
}

enum class TriState {
    DEFAULT,
    TRUE,
    FALSE;

    /**
     * @return true if this is [TRUE]
     */
    fun toBoolean(): Boolean = this == TRUE
}

/**
 * @return [TriState.DEFAULT] if null, [TriState.FALSE] if false, otherwise true
 */
fun Boolean?.toTriState() = when (this) {
    null -> TriState.DEFAULT
    false -> TriState.FALSE
    true -> TriState.TRUE
}