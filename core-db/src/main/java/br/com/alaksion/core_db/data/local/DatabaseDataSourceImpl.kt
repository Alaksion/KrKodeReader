package br.com.alaksion.core_db.data.local

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.ScanData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    private val database: Database,
) : DatabaseDataSource {

    override fun storeScan(scan: ScanData): Flow<ScanData> {
        return flow {
            val insertedItemId = database.qrScanDao().create(scan)
            emit(database.qrScanDao().getById(insertedItemId))
        }

    }

    override fun getScans(): Flow<List<ScanData>> {
        return flow { emit(database.qrScanDao().index()) }
    }

}