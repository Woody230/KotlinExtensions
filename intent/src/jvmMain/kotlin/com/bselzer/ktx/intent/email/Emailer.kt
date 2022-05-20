package com.bselzer.ktx.intent.email

import com.bselzer.ktx.intent.JvmIntent
import java.awt.Desktop
import java.net.URI
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

internal actual class AppEmailer actual constructor() : JvmIntent(), Emailer {
    override fun send(email: Email): Boolean {
        return try {
            val desktop = desktop ?: return false
            if (!desktop.isSupported(Desktop.Action.MAIL)) {
                return false
            }

            // TODO is there a standard way to handle mailto creation to avoid doing all of this?
            fun String.encoded() = URLEncoder.encode(this, StandardCharsets.UTF_8).replace("+", "%20")
            val mailto = buildString {
                val separator = ","
                append("mailto:?to=")
                append(email.to.joinToString(separator = separator))

                append("&cc=")
                append(email.cc.joinToString(separator = separator))

                append("&bcc=")
                append(email.bcc.joinToString(separator = separator))

                append("&subject=")
                append(email.subject?.encoded() ?: "")

                append("&body=")
                append(email.body?.encoded() ?: "")
            }

            val uri = URI.create(mailto)
            desktop.mail(uri)
            true
        } catch (ex: Exception) {
            logger.log(System.Logger.Level.ERROR, "Failed to send $email", ex)
            false
        }
    }
}