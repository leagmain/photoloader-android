package com.leagmain.android.photoloader

import androidx.lifecycle.LiveData

interface PhotoLoader {
    val photos: LiveData<List<Photo>>
    fun load()
}