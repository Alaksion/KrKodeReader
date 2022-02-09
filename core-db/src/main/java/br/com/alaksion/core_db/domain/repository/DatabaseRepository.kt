package br.com.alaksion.core_db.domain.repository

import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan

interface DatabaseRepository {

    suspend fun storeScan(scan: CreateScanRequest)

    suspend fun listScans(): List<Scan>

}