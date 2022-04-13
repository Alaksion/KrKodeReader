package br.com.alaksion.core_db.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alaksion.core_db.data.model.ScanData

@Database(entities = [ScanData::class], version = 1)
abstract class QrDatabase : RoomDatabase() {

    abstract fun qrScanDao(): QrScanDAO

}