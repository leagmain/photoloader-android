package com.leagmain.android.photoloader

import android.content.ContentResolver
import android.database.Cursor
import android.provider.MediaStore

internal val ContentResolver.photoCursor: Cursor?
    get() = query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        null,
        null,
        null,
        null
    )

fun ContentResolver.loadPhotos(): List<Photo> {
    val cursor = photoCursor ?: return emptyList()
    val photos = cursor.readPhotos()
    cursor.close()
    return photos
}

fun ContentResolver.loadPhotosAsJsonString(): String {
    val cursor = photoCursor ?: return ""
    val photosJsonString = cursor.readPhotosAsJsonString()
    cursor.close()
    return photosJsonString
}