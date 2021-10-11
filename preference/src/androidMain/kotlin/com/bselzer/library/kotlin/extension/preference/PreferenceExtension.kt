package com.bselzer.library.kotlin.extension.preference

import androidx.preference.Preference
import androidx.preference.PreferenceScreen

/**
 * Adds [this] preference to the [screen]
 */
fun Preference.addTo(screen: PreferenceScreen) = screen.addPreference(this)