package com.bselzer.ktx.intent

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.util.Log

abstract class AndroidIntent : ContentProvider() {
    protected val tag = this::class.simpleName

    private companion object {
        var applicationContext: Context? = null
    }

    override fun onCreate(): Boolean {
        val context = context
        if (context == null) {
            Log.i(tag, "Context is required but it is null.")
        } else {
            applicationContext = context.applicationContext
        }

        return true
    }

    fun requireApplicationContext(): Context = requireNotNull(applicationContext) { "Context is required but it is not initialized." }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun getType(uri: Uri): String? = null
}