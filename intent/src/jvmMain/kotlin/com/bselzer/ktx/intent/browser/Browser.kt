package com.bselzer.ktx.intent.browser

import com.bselzer.ktx.intent.JvmIntent
import com.bselzer.ktx.logging.Logger
import java.awt.Desktop
import java.net.URI

internal actual class SystemBrowser actual constructor() : JvmIntent(), Browser {
    override fun open(uri: String): Boolean {
        return try {
            val desktop = desktop ?: return false
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                return false
            }

            desktop.browse(URI.create(uri))
            true
        } catch (ex: Exception) {
            Logger.e(ex) { "Failed to open the uri $uri." }
            false
        }
    }
}