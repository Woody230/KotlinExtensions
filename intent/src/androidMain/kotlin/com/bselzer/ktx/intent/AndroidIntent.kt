package com.bselzer.ktx.intent

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import com.bselzer.ktx.logging.Logger
import kotlin.reflect.KClass

abstract class AndroidIntent : ContentProvider() {
    private companion object {
        val applicationContext: MutableMap<KClass<*>, Context> = mutableMapOf()
    }

    override fun onCreate(): Boolean {
        val context = context
        if (context == null) {
            Logger.i { "Context is required but it is null." }
        } else {
            applicationContext[this::class] = context.applicationContext
        }

        return true
    }

    fun requireApplicationContext(): Context = requireNotNull(applicationContext[this::class]) { "Context is required but it is not initialized." }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun getType(uri: Uri): String? = null
}