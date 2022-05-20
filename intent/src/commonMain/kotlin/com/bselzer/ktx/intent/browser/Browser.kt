package com.bselzer.ktx.intent.browser

interface Browser {

    /**
     * Opens the browser and navigates to the given [uri].
     *
     * @return true if successful
     */
    fun open(uri: String): Boolean

    companion object : Browser by AppBrowser()
}

internal expect class AppBrowser() : Browser

