package com.bselzer.ktx.intent.browser

import java.awt.Desktop
import java.net.URI

actual class AppBrowser actual constructor() : Browser {
    private val desktop: Desktop?
        get() = if (Desktop.isDesktopSupported()) Desktop.getDesktop() else null

    private val logger = System.getLogger(this::class.simpleName)

    override fun open(url: String): Boolean {
        return try {
            val uri = URI.create(url)
            val desktop = desktop ?: return false
            if (!desktop.isSupported(Desktop.Action.BROWSE)) {
                return false
            }

            desktop.browse(uri)
            true
        } catch (ex: Exception) {
            logger.log(System.Logger.Level.ERROR, "Failed to open the url $url.", ex)
            false
        }
    }
}