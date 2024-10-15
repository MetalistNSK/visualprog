package com.example.visualprog

import android.Manifest
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.visualprog.cellinfo.CellInfoModule
import com.example.visualprog.location.LocationModule
import com.example.visualprog.permissions.PermissionManager
import com.example.visualprog.ui.UIManager

class MainActivity : AppCompatActivity() {

    private lateinit var locationModule: LocationModule
    private lateinit var permissionManager: PermissionManager
    private lateinit var cellInfoModule: CellInfoModule
    private lateinit var uiManager: UIManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Инициализация UI
        val latitudeTextView: TextView = findViewById(R.id.Latitude)
        val longitudeTextView: TextView = findViewById(R.id.Longitude)
        val cellInfoTextView: TextView = findViewById(R.id.CellInfo)

        uiManager = UIManager(latitudeTextView, longitudeTextView, cellInfoTextView)

        // Инициализация модулей
        locationModule = LocationModule(this) { latitude, longitude ->
            uiManager.updateLocation(latitude, longitude)
        }

        cellInfoModule = CellInfoModule(this) { cellInfo ->
            uiManager.updateCellInfo(cellInfo)
        }

        permissionManager = PermissionManager(this) {
            locationModule.startLocationUpdates()
            cellInfoModule.fetchCellInfo()
        }

        // Запрос разрешений
        permissionManager.checkAndRequestPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun onResume() {
        super.onResume()
        if (permissionManager.isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationModule.startLocationUpdates()
        }
    }

    override fun onPause() {
        super.onPause()
        locationModule.stopLocationUpdates()
    }
}
