package br.com.alaksion.core_db.data.local

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.ScanData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    private val database: Database,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DatabaseDataSource {

    override suspend fun storeScan(scan: ScanData) {
        withContext(dispatcher) {
            database.qrScanDao().create(scan)
        }
    }

    override suspend fun getScans(): List<ScanData> {
        return withContext(dispatcher) {
            database.qrScanDao().index()
        }
    }

}