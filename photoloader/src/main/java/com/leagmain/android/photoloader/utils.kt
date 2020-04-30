package com.leagmain.android.photoloader

import android.database.Cursor
import androidx.core.database.getBlobOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import com.squareup.moshi.Moshi

internal fun Cursor.readPhotos(moshi: Moshi): List<Photo> {
    val photos = mutableListOf<Photo>()
    while (moveToNext()) {
        val values = mutableMapOf<String, Any?>()
        for (index in 0 until columnCount) {
            val columnName = getColumnName(index)
            val value: Any? = when (getType(index)) {
                Cursor.FIELD_TYPE_BLOB -> {
                    getBlobOrNull(index)
                }
                Cursor.FIELD_TYPE_FLOAT -> {
                    getFloatOrNull(index)
                }
                Cursor.FIELD_TYPE_INTEGER -> {
                    getIntOrNull(index)
                }
                Cursor.FIELD_TYPE_STRING -> {
                    getStringOrNull(index)
                }
                else -> {
                    null
                }
            }

            value ?: continue
            values[columnName] = value
        }
        val json = moshi.adapter(Map::class.java).toJson(values)
        val photo = PhotoJsonAdapter(moshi).fromJson(json) ?: continue
        photos.add(photo)
    }
    return photos
}