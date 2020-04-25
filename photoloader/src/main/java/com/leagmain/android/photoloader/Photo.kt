package com.leagmain.android.photoloader

import android.provider.BaseColumns
import android.provider.MediaStore
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Photo(
    @Json(name = MediaStore.MediaColumns.DISPLAY_NAME)
    val displayName: String,
    @Json(name = MediaStore.MediaColumns.TITLE)
    val title: String,
    @Json(name = BaseColumns._ID)
    val id: String,
    @Json(name = MediaStore.MediaColumns.DATA)
    val dataPath: String?,
    @Json(name = MediaStore.MediaColumns.MIME_TYPE)
    val mimeType: String?
)