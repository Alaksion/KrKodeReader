package br.com.alaksion.qrcodereader.success

import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan

internal object SuccessVmTestData {

    val scanRequest = CreateScanRequest(
        code = "code",
        title = "title"
    )

    val scanResponse = Scan(
        code = "code",
        title = "title",
        id = 1,
        createdAt = "createdAt"
    )

}