package com.bselzer.ktx.intent.browser

import com.bselzer.ktx.intent.JvmIntent
import java.awt.Desktop
import java.net.URI

internal actual class AppBrowser actual constructor() : JvmIntent(), Browser {
    override fun open(uri: String): Boolean {
        return try {
            val desktop = desktop ?: return false
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                return false
            }

            desktop.browse(URI.create(uri))
            true
        } catch (ex: Exception) {
            logger.log(System.Logger.Level.ERROR, "Failed to open the uri $uri.", ex)
            false
        }
    }
}