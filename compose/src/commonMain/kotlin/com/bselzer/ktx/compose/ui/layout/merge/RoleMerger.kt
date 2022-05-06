package com.bselzer.ktx.compose.ui.layout.merge

import androidx.compose.runtime.Stable
import androidx.compose.ui.semantics.Role

class RoleMerger : ComponentMerger<Role?> {
    override val default: Role? = null

    companion object {
        @Stable
        val Default: Role? = null
    }
}