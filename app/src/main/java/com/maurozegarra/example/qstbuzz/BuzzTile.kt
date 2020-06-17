package com.maurozegarra.example.qstbuzz

import android.content.Context
import android.graphics.drawable.Icon
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val NO_BUZZ_PATTERN = longArrayOf(0)


@RequiresApi(Build.VERSION_CODES.N)
class BuzzTile : TileService() {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    override fun onTileAdded() {
        super.onTileAdded()

        qsTile.state = Tile.STATE_INACTIVE

        qsTile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()

        qsTile.icon = Icon.createWithResource(this, R.drawable.ic_buzz_white_24)
        qsTile.label = getString(R.string.label)
        qsTile.contentDescription = getString(R.string.label)

        qsTile.updateTile()
    }

    override fun onClick() {
        super.onClick()

        if (qsTile.state == Tile.STATE_INACTIVE) {
            // Turn on
            qsTile.state = Tile.STATE_ACTIVE
            buzz(BuzzType.CORRECT.pattern)
        } else {
            // Turn off
            qsTile.state = Tile.STATE_INACTIVE
        }

        qsTile.updateTile()
    }

    private fun buzz(pattern: LongArray) {
        val buzzer = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        buzzer.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(pattern, -1)
            }
        }
    }
}