package pl.matiu.kalistenika.media

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.remember
import pl.matiu.kalistenika.R


object StartSong {
    private lateinit var context: Context

    val seriesSong: MediaPlayer by lazy {
        MediaPlayer.create(context, R.raw.dzwonek)
    }

    val breakSong: MediaPlayer by lazy {
        MediaPlayer.create(context, R.raw.breaksong)
    }

    fun init(context: Context) {
        this.context = context
    }
}