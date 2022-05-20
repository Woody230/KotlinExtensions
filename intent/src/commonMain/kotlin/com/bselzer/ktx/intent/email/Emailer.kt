package com.bselzer.ktx.intent.email

interface Emailer {
    /**
     * Opens an emailing application with the given [email] data pre-populated.
     *
     * @return true if successful
     */
    fun send(email: Email): Boolean

    companion object : Emailer by AppEmailer()
}

internal expect class AppEmailer() : Emailer