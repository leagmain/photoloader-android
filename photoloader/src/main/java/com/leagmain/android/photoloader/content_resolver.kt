package com.leagmain.android.photoloader

import android.content.ContentResolver
import android.provider.MediaStore

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