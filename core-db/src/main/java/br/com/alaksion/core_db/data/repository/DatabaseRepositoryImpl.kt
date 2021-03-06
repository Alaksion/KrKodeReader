package br.com.alaksion.core_db.data.repository

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.mapToData
import br.com.alaksion.core_db.data.model.mapToScan
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DatabaseRepositoryImpl @Inject constructor(
    private val localDataSource: DatabaseDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : DatabaseRepository {

    override fun storeScan(scan: CreateScanRequest): Flow<Scan> {
        return localDataSource.storeScan(scan.mapToData()).map { it.mapToScan() }.flowOn(dispatcher)
    }

    override fun listScans(): Flow<List<Scan>> {
        return localDataSource.getScans().map { scansList ->
            scansList.map { scan -> scan.mapToScan() }
        }.flowOn(dispatcher)
    }

    override fun deleteScan(scan: Scan) {
        localDataSource.deleteScan(scan.mapToData())
    }

    override fun getScan(scandId: Long): Flow<Scan> {
        return localDataSource.getScan(scandId).map { it.mapToScan() }.flowOn(dispatcher)
    }

}