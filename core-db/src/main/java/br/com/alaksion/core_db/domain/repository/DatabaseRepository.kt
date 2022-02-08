package br.com.alaksion.core_db.domain.repository

import br.com.alaksion.core_db.domain.model.CreateScanRequest

interface DatabaseRepository {

    suspend fun storeScan(scan: CreateScanRequest)

}