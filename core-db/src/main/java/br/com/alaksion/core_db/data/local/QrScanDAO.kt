package br.com.alaksion.core_db.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alaksion.core_db.data.model.ScanData

@Dao
interface QrScanDAO {

    @Insert
    suspend fun create(data: ScanData)

    @Query("select * from scans order by id DESC")
    suspend fun index(): List<ScanData>

}