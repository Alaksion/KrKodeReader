package br.com.alaksion.qrcodereader.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alaksion.qrcodereader.domain.model.CreateScanRequest
import br.com.alaksion.qrcodereader.utils.extensions.formatCurrentDate
import java.util.*

@Entity
data class ScanData(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "title") val title: String
)

fun CreateScanRequest.mapToData() = ScanData(
    code = this.code,
    title = this.title,
    createdAt = Date().formatCurrentDate(),
    id = 0
)
