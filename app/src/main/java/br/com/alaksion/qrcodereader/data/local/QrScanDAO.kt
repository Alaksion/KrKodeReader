package br.com.alaksion.qrcodereader.data.local

import androidx.room.Dao
import androidx.room.Insert
import br.com.alaksion.qrcodereader.data.model.ScanData

@Dao
interface QrScanDAO {

    @Insert
    suspend fun create(data: ScanData)

}