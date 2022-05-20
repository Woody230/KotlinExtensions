package com.bselzer.ktx.intent.browser

import android.content.Intent
import android.net.Uri
import android.util.Log
import com.bselzer.ktx.intent.AndroidIntent

internal actual class AppBrowser actual constructor() : AndroidIntent(), Browser {
    override fun open(uri: String): Boolean {
        return try {
            val context = context ?: return false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
            context.startActivity(intent)
            true
        } catch (ex: Exception) {
            Log.e(tag, "Failed to open the uri $uri", ex)
            false
        }
    }
}