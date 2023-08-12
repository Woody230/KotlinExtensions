package com.bselzer.ktx.intent.email

import android.content.Intent
import android.net.Uri
import com.bselzer.ktx.intent.AndroidIntent
import com.bselzer.ktx.logging.Logger

internal actual class SystemEmailer actual constructor() : AndroidIntent(), Emailer {
    override fun send(email: Email): Boolean {
        return try {
            val context = requireApplicationContext()

            // https://developer.android.com/guide/components/intents-common#ComposeEmail
            // SENDTO for no attachment
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                // Sending this outside of the activity context so need to use this flag.
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

                // Data as mailto to only allow email apps to handle this intent.
                setDataAndType(Uri.parse("mailto:"), "*/*")

                putExtra(Intent.EXTRA_EMAIL, email.to.toTypedArray())
                putExtra(Intent.EXTRA_CC, email.cc.toTypedArray())
                putExtra(Intent.EXTRA_BCC, email.bcc.toTypedArray())
                putExtra(Intent.EXTRA_SUBJECT, email.subject ?: "")
                putExtra(Intent.EXTRA_TEXT, email.body ?: "")
            }

            context.startActivity(intent)
            true
        } catch (ex: Exception) {
            Logger.e(ex) { "Failed to send $email" }
            false
        }
    }
}