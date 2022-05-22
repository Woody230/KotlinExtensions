package com.bselzer.ktx.intent.browser

import android.content.Intent
import android.net.Uri
import com.bselzer.ktx.intent.AndroidIntent
import com.bselzer.ktx.logging.Logger

internal actual class SystemBrowser actual constructor() : AndroidIntent(), Browser {
    override fun open(uri: String): Boolean {
        return try {
            val context = requireApplicationContext()
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri)).apply {
                // Sending this outside of the activity context so need to use this flag.
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            context.startActivity(intent)
            true
        } catch (ex: Exception) {
            Logger.e(ex) { "Failed to open the uri $uri" }
            false
        }
    }
}