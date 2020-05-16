package com.leagmain.android.photoloader

import android.content.ContentResolver
import android.provider.MediaStore
import com.squareup.moshi.Moshi

internal val ContentResolver.moshi :Moshi by lazy {
        Moshi.Builder().build()
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