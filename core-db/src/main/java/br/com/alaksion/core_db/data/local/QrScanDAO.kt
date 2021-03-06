package br.com.alaksion.core_db.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import br.com.alaksion.core_db.data.model.ScanData

@Dao
interface QrScanDAO {

    @Insert
    suspend fun create(data: ScanData): Long

    @Query("select * from scans order by id DESC")
    suspend fun index(): List<ScanData>

    @Query("select * from scans where id = :id")
    suspend fun getById(id: Long): ScanData

    @Delete
    suspend fun delete(scanData: ScanData)

}