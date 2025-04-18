package com.rahulc0dy.pokedex.composables

import android.media.AudioAttributes
import android.media.MediaPlayer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri

@Composable
fun CryPlayer(cryUrl: String, primaryColor: androidx.compose.ui.graphics.Color) {
    val context = LocalContext.current

    // Remember one MediaPlayer instance
    val mediaPlayer = remember {
        MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build()
            )
            setOnCompletionListener { it.reset() }
        }
    }

    // Release on dispose
    DisposableEffect(cryUrl) {
        onDispose { mediaPlayer.release() }
    }

    OutlinedButton(
        onClick = {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(context, cryUrl.toUri())
            mediaPlayer.prepareAsync()
            mediaPlayer.setOnPreparedListener { player -> player.start() }
        },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "▶ Play Cry",
            modifier = Modifier
                .padding(vertical = 4.dp),
            color = primaryColor
        )
    }


}
