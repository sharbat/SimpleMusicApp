package com.example.musicplay

import android.media.MediaPlayer

class MyMediaPlayer: MediaPlayer() {
    private var source: String? = null

    override fun setDataSource(path: String?) {
        super.setDataSource(path)
        source = path
    }

    fun getDataSource(): String?{
        return source
    }
}