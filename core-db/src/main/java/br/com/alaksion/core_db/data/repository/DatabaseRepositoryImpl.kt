package br.com.alaksion.core_db.data.repository

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.mapToData
import br.com.alaksion.core_db.data.model.mapToScan
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val localDataSource: DatabaseDataSource
) : DatabaseRepository {

    override suspend fun storeScan(scan: CreateScanRequest) {
        localDataSource.storeScan(scan.mapToData())
    }

    override suspend fun listScans(): List<Scan> {
        return localDataSource.getScans().map { it.mapToScan() }
    }

}