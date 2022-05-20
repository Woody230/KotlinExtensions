package com.bselzer.ktx.intent.email

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.bselzer.ktx.intent.AndroidIntent

internal actual class AppEmailer actual constructor() : AndroidIntent(), Emailer {
    override fun send(email: Email): Boolean {
        return try {
            val context = context ?: return false

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
            Log.e(tag, "Failed to send $email", ex)
            false
        }
    }
}