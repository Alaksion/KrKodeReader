package br.com.alaksion.core_db.data.repository

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.mapToData
import br.com.alaksion.core_db.data.model.mapToScan
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DatabaseRepositoryImpl @Inject constructor(
    private val localDataSource: DatabaseDataSource
) : DatabaseRepository {

    override fun storeScan(scan: CreateScanRequest): Flow<Scan> {
        return localDataSource.storeScan(scan.mapToData()).map { it.mapToScan() }
    }

    override fun listScans(): Flow<List<Scan>> {
        return localDataSource.getScans().map { scansList ->
            scansList.map { scan -> scan.mapToScan() }
        }
    }

}