package com.example.visualprog.cellinfo

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.telephony.*
import androidx.core.app.ActivityCompat

class CellInfoModule(
    private val context: Context,
    private val onCellInfoFetched: (String) -> Unit
) {

    private val telephonyManager: TelephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

    @SuppressLint("MissingPermission")
    fun fetchCellInfo() {
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            val cellInfoList = telephonyManager.allCellInfo
            val cellInfoStringBuilder = StringBuilder()

            if (cellInfoList != null) {
                for (cellInfo in cellInfoList) {
                    when (cellInfo) {
                        is CellInfoGsm -> {
                            cellInfoStringBuilder.append("Type: GSM\n")
                            cellInfoStringBuilder.append("Cell ID: ${cellInfo.cellIdentity.cid}\n")
                            cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength.dbm} dBm\n")
                            cellInfoStringBuilder.append("Location Area Code: ${cellInfo.cellIdentity.lac}\n")
                            cellInfoStringBuilder.append("Operator: ${cellInfo.cellIdentity.mobileNetworkOperator}\n")
                        }
                        is CellInfoLte -> {
                            cellInfoStringBuilder.append("Type: LTE\n")
                            cellInfoStringBuilder.append("Cell ID: ${cellInfo.cellIdentity.ci}\n")
                            cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength.dbm} dBm\n")
                            cellInfoStringBuilder.append("Tracking Area Code: ${cellInfo.cellIdentity.tac}\n")
                            cellInfoStringBuilder.append("Operator: ${cellInfo.cellIdentity.mobileNetworkOperator}\n")
                        }
                        is CellInfoWcdma -> {
                            cellInfoStringBuilder.append("Type: WCDMA\n")
                            cellInfoStringBuilder.append("Cell ID: ${cellInfo.cellIdentity.cid}\n")
                            cellInfoStringBuilder.append("Signal Strength: ${cellInfo.cellSignalStrength.dbm} dBm\n")
                            cellInfoStringBuilder.append("Location Area Code: ${cellInfo.cellIdentity.lac}\n")
                            cellInfoStringBuilder.append("Operator: ${cellInfo.cellIdentity.mobileNetworkOperator}\n")
                        }
                    }
                    cellInfoStringBuilder.append("\n")
                }
                onCellInfoFetched(cellInfoStringBuilder.toString())
            } else {
                onCellInfoFetched("No cell info available")
            }
        }
    }
}
