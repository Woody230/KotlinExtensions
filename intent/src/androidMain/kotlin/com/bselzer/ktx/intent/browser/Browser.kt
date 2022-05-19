package com.bselzer.ktx.intent.browser

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.util.Log

actual class AppBrowser actual constructor() : Browser, ContentProvider() {
    private val tag = this::class.simpleName

    override fun onCreate(): Boolean {
        if (context == null) {
            Log.i(tag, "Context is required to use the browser but it is null.")
        }

        return true
    }

    override fun open(url: String): Boolean {
        return try {
            val context = context ?: return false
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(intent)
            true
        } catch (ex: Exception) {
            Log.e(tag, "Failed to open the url $url")
            false
        }
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun getType(uri: Uri): String? = null
}