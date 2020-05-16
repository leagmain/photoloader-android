package com.leagmain.android.photoloader

import android.content.ContentResolver
import android.provider.MediaStore
import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import java.util.*

internal val ContentResolver.moshi: Moshi by lazy {
    Moshi.Builder()
        .add(object {
            @FromJson
            fun fromJson(long: Long?): Date? {
                long ?: return null
                return Date(long)
            }

            @ToJson
            fun toJson(date: Date?): Long? {
                date ?: return null
                return date.time
            }
        })
        .build()
}

fun ContentResolver.loadPhotos(): List<Photo> {
    val cursor = query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        null
    ) ?: return emptyList()
    val photos = cursor.readPhotos(moshi)
    cursor.close()
    return photos
}