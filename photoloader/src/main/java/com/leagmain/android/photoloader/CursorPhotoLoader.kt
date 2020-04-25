package com.leagmain.android.photoloader

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.core.database.getBlobOrNull
import androidx.core.database.getFloatOrNull
import androidx.core.database.getIntOrNull
import androidx.core.database.getStringOrNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.loader.app.LoaderManager
import androidx.loader.content.CursorLoader
import androidx.loader.content.Loader
import com.squareup.moshi.Moshi

fun photoLoader(context: Context, loaderManager: LoaderManager): PhotoLoader =
    CursorPhotoLoader(context, loaderManager)

internal class CursorPhotoLoader(
    private val context: Context,
    private val loaderManager: LoaderManager
) : PhotoLoader {

    companion object {
        private const val _ID = 0x9899
    }

    private val _photos = MutableLiveData<List<Photo>>()

    private val moshi: Moshi by lazy {
        Moshi.Builder().build()
    }

    override val photos: LiveData<List<Photo>>
        get() = _photos

    override fun load() {
        loaderManager.initLoader(_ID, null, object : LoaderManager.LoaderCallbacks<Cursor> {
            override fun onCreateLoader(id: Int, args: Bundle?): Loader<Cursor> {
                val cursorLoader = CursorLoader(context)
                cursorLoader.uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                return cursorLoader
            }

            override fun onLoadFinished(loader: Loader<Cursor>, data: Cursor?) {
                if (loader.id != _ID || data == null) return
                with(data) {
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

                    _photos.postValue(photos)
                }
            }

            override fun onLoaderReset(loader: Loader<Cursor>) {
                if (loader.id == _ID) {
                    _photos.value = emptyList()
                }
            }
        })
    }
}