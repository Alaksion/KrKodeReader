package br.com.alaksion.core_db.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_utils.extensions.formatCurrentDate
import java.util.*

@Entity(tableName = "scans")
data class ScanData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "code") val code: String,
    @ColumnInfo(name = "created_at") val createdAt: String,
    @ColumnInfo(name = "title") val title: String
)

fun CreateScanRequest.mapToData() = ScanData(
    code = this.code,
    title = this.title,
    createdAt = Date().formatCurrentDate()
)

fun ScanData.mapToScan() = Scan(
    code = this.code,
    createdAt = this.createdAt,
    title = this.title,
    id = this.id
)

fun Scan.mapToData() = ScanData(
    code = this.code,
    createdAt = this.createdAt,
    title = this.title,
    id = this.id
)