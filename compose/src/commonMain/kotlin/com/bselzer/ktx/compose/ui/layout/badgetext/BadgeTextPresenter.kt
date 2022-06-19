package com.bselzer.ktx.compose.ui.layout.badgetext

import androidx.compose.runtime.Stable
import com.bselzer.ktx.compose.ui.layout.badge.BadgePresenter
import com.bselzer.ktx.compose.ui.layout.modifier.presentable.PresentableModifier
import com.bselzer.ktx.compose.ui.layout.project.Presenter
import com.bselzer.ktx.compose.ui.layout.text.TextPresenter

data class BadgeTextPresenter(
    override val modifier: PresentableModifier = PresentableModifier,
    val badge: BadgePresenter = BadgePresenter.Default,
    val text: TextPresenter = TextPresenter.Default
) : Presenter<BadgeTextPresenter>(modifier) {
    companion object {
        @Stable
        val Default = BadgeTextPresenter()
    }

    override fun safeMerge(other: BadgeTextPresenter) = BadgeTextPresenter(
        modifier = modifier.merge(other.modifier),
        badge = badge.merge(other.badge),
        text = text.merge(other.text)
    )
}