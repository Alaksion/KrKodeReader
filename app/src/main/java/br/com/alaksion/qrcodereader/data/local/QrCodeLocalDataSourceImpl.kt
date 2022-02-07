package br.com.alaksion.qrcodereader.data.local

import br.com.alaksion.qrcodereader.data.datasource.QrCodeLocalDataSource
import br.com.alaksion.qrcodereader.data.model.ScanData
import javax.inject.Inject

class QrCodeLocalDataSourceImpl @Inject constructor(
    private val database: Database

) : QrCodeLocalDataSource {

    override suspend fun storeScan(scan: ScanData) {
        database.qrScanDao().create(scan)
    }

}