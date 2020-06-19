package com.maurozegarra.example.qstbuzz

import android.app.IntentService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

private val CORRECT_BUZZ_PATTERN = longArrayOf(100, 100, 100, 100, 100, 100)
private val GAME_OVER_BUZZ_PATTERN = longArrayOf(0, 2000)
private val PANIC_BUZZ_PATTERN = longArrayOf(0, 200)
private val NO_BUZZ_PATTERN = longArrayOf(0)

class BuzzService : IntentService("BuzzService") {
    enum class BuzzType(val pattern: LongArray) {
        CORRECT(CORRECT_BUZZ_PATTERN),
        GAME_OVER(GAME_OVER_BUZZ_PATTERN),
        COUNTDOWN_PANIC(PANIC_BUZZ_PATTERN),
        NO_BUZZ(NO_BUZZ_PATTERN)
    }

    override fun onHandleIntent(intent: Intent?) {
        val buzzer = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        buzzer.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                buzzer.vibrate(VibrationEffect.createWaveform(BuzzType.CORRECT.pattern, -1))
            } else {
                //deprecated in API 26
                buzzer.vibrate(BuzzType.CORRECT.pattern, -1)
            }
        }
    }
}
