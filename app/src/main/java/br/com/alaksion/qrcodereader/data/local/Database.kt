package br.com.alaksion.qrcodereader.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import br.com.alaksion.qrcodereader.data.model.ScanData

@Database(entities = [ScanData::class], version = 1)
abstract class Database : RoomDatabase() {

    abstract fun qrScanDao(): QrScanDAO

}