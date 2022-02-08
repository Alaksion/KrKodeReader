package br.com.alaksion.core_db.data.local

import androidx.room.Dao
import androidx.room.Insert
import br.com.alaksion.core_db.data.model.ScanData

@Dao
interface QrScanDAO {

    @Insert
    suspend fun create(data: ScanData)

}