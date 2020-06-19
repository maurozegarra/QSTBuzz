package com.maurozegarra.example.qstbuzz

import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Build
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.N)
class BuzzTile : TileService() {

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
            buzz()
        } else {
            // Turn off
            qsTile.state = Tile.STATE_INACTIVE
        }

        qsTile.updateTile()
    }

    private fun buzz() {
        val intent = Intent(this, BuzzService::class.java)
        startService(intent)
    }
}
