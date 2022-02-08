package br.com.alaksion.core_db.data.local

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.ScanData
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    private val database: Database

) : DatabaseDataSource {

    override suspend fun storeScan(scan: ScanData) {
        database.qrScanDao().create(scan)
    }

}