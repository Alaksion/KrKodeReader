package br.com.alaksion.core_db.domain.repository

import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import kotlinx.coroutines.flow.Flow

interface DatabaseRepository {

    fun storeScan(scan: CreateScanRequest): Flow<Scan>

    fun listScans(): Flow<List<Scan>>

    fun deleteScan(scan: Scan)

    fun getScan(scandId: Long): Flow<Scan>

}