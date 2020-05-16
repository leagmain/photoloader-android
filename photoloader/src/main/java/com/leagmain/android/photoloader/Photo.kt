package com.leagmain.android.photoloader

import android.os.Build
import android.provider.BaseColumns
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.*

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
    val mimeType: String?,

    @Json(name = MediaStore.MediaColumns.DATE_ADDED)
    val addedAt: Date,

    @Json(name = MediaStore.MediaColumns.DATE_MODIFIED)
    val modifiedAt: Date,

    @Json(name = MediaStore.MediaColumns.SIZE)
    val size: Int?,

    @Json(name = MediaStore.MediaColumns.WIDTH)
    val width: Int?,

    @Json(name = MediaStore.MediaColumns.HEIGHT)
    val height: Int?
) {

    @RequiresApi(Build.VERSION_CODES.Q)
    @Json(name = MediaStore.MediaColumns.ORIENTATION)
    var orientation: Int? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @Json(name = MediaStore.MediaColumns.DATE_TAKEN)
    var takenAt: Date? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @Json(name = MediaStore.MediaColumns.BUCKET_ID)
    var bucketId: Int? = null

    @RequiresApi(Build.VERSION_CODES.Q)
    @Json(name = MediaStore.MediaColumns.BUCKET_DISPLAY_NAME)
    val bucketName: String? = null
}