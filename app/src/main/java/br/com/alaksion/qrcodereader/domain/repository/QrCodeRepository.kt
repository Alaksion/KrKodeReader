package br.com.alaksion.qrcodereader.domain.repository

import br.com.alaksion.qrcodereader.domain.model.CreateScanRequest

interface QrCodeRepository {

    suspend fun storeScan(scan: CreateScanRequest)

}