package com.leagmain.android.photoloader

import com.squareup.moshi.Moshi

internal val moshi: Moshi by lazy {
    Moshi.Builder().build()
}

interface PhotoLoader {
    suspend fun load(): List<Photo>
}