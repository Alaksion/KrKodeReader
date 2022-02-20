package br.com.alaksion.core_db.data.datasource

import br.com.alaksion.core_db.data.model.ScanData
import kotlinx.coroutines.flow.Flow

interface DatabaseDataSource {

    fun storeScan(scan: ScanData): Flow<ScanData>

    fun getScans(): Flow<List<ScanData>>

    fun deleteScan(scan: ScanData)

    fun getScan(scanId: Long): Flow<ScanData>

}