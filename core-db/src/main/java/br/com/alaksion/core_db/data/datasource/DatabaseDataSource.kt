package br.com.alaksion.core_db.data.datasource

import br.com.alaksion.core_db.data.model.ScanData

interface DatabaseDataSource {

    suspend fun storeScan(scan: ScanData)

    suspend fun getScans(): List<ScanData>

}