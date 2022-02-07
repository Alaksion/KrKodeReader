package br.com.alaksion.qrcodereader.data.repository

import br.com.alaksion.qrcodereader.data.datasource.QrCodeLocalDataSource
import br.com.alaksion.qrcodereader.data.model.mapToData
import br.com.alaksion.qrcodereader.domain.model.CreateScanRequest
import br.com.alaksion.qrcodereader.domain.repository.QrCodeRepository
import javax.inject.Inject

class QrCodeRepositoryImpl @Inject constructor(
    private val localDataSource: QrCodeLocalDataSource
) : QrCodeRepository {

    override suspend fun storeScan(scan: CreateScanRequest) {
        localDataSource.storeScan(scan.mapToData())
    }

}