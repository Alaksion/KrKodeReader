package br.com.alaksion.core_db.data.local

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.ScanData
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class DatabaseDataSourceImpl @Inject constructor(
    private val database: Database,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DatabaseDataSource {

    override fun storeScan(scan: ScanData): Flow<ScanData> {
        return flow {
            val insertedItemId = database.qrScanDao().create(scan)
            emit(database.qrScanDao().getById(insertedItemId))
        }.flowOn(dispatcher)
    }

    override fun getScans(): Flow<List<ScanData>> {
        return flow { emit(database.qrScanDao().index()) }.flowOn(dispatcher)
    }

    override fun deleteScan(scan: ScanData) {
        CoroutineScope(dispatcher).launch {
            database.qrScanDao().delete(scan)
        }
    }
}