package com.example.visualprog.ui

import android.widget.TextView

class UIManager(
    private val latitudeTextView: TextView,
    private val longitudeTextView: TextView,
    private val cellInfoTextView: TextView
) {
    fun updateLocation(latitude: Double, longitude: Double) {
        latitudeTextView.text = "Latitude: $latitude"
        longitudeTextView.text = "Longitude: $longitude"
    }

    fun updateCellInfo(cellInfo: String) {
        cellInfoTextView.text = "Cell Info:\n$cellInfo"
    }
}
