package br.com.alaksion.qrcodereader.data.datasource

import br.com.alaksion.qrcodereader.data.model.ScanData

interface QrCodeLocalDataSource {

    suspend fun storeScan(scan: ScanData)

}