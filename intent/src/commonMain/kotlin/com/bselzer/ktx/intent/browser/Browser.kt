package com.bselzer.ktx.intent.browser

interface Browser {

    /**
     * Opens the browser and navigates to the given [url].
     *
     * @return true if successful
     */
    fun open(url: String): Boolean

    companion object : Browser by AppBrowser()
}

expect class AppBrowser() : Browser

