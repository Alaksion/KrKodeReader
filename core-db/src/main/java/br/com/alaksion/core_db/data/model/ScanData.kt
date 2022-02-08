package br.com.alaksion.core_db.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_utils.extensions.formatCurrentDate
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
    id = 0,
    createdAt = Date().formatCurrentDate()
)