package br.com.alaksion.core_db.data.repository.testdata

import br.com.alaksion.core_db.data.model.ScanData
import br.com.alaksion.core_db.data.model.mapToScan
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_utils.extensions.formatCurrentDate
import java.util.*

internal object DatabaseRepositoryTestData {

    val createScanRequest = CreateScanRequest(
        code = "code",
        title = "title"
    )

    val scanData = ScanData(
        id = 0,
        code = "code",
        createdAt = Date().formatCurrentDate(),
        title = "title"
    )

    val scan = scanData.mapToScan()

    val scans = listOf(scanData)

}