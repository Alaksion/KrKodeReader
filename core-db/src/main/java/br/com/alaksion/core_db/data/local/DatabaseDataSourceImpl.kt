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

internal class DatabaseDataSourceImpl @Inject constructor(
    private val qrDatabase: QrDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DatabaseDataSource {

    override fun storeScan(scan: ScanData): Flow<ScanData> {
        return flow {
            val insertedItemId = qrDatabase.qrScanDao().create(scan)
            emit(qrDatabase.qrScanDao().getById(insertedItemId))
        }.flowOn(dispatcher)
    }

    override fun getScans(): Flow<List<ScanData>> {
        return flow { emit(qrDatabase.qrScanDao().index()) }.flowOn(dispatcher)
    }

    override fun deleteScan(scan: ScanData) {
        CoroutineScope(dispatcher).launch {
            qrDatabase.qrScanDao().delete(scan)
        }
    }

    override fun getScan(scanId: Long): Flow<ScanData> {
        return flow { emit(qrDatabase.qrScanDao().getById(scanId)) }
    }
}