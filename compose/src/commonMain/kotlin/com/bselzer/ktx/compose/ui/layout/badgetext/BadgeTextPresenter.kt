package com.bselzer.ktx.compose.ui.layout.badgetext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.badge.BadgePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.PresentableModifiers
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class BadgeTextPresenter(
    override val modifiers: PresentableModifiers = PresentableModifiers.Default,
    val badge: BadgePresenter = BadgePresenter.Default,
    val text: TextPresenter = TextPresenter.Default
) : Presenter<BadgeTextPresenter>(modifiers) {
    companion object {
        @Stable
        val Default = BadgeTextPresenter()
    }

    override fun safeMerge(other: BadgeTextPresenter) = BadgeTextPresenter(
        modifiers = modifiers.merge(other.modifiers),
        badge = badge.merge(other.badge),
        text = text.merge(other.text)
    )

    @Composable
    override fun localized() = copy(
        badge = badge.localized(),
        text = text.localized()
    )
}