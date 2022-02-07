package br.com.alaksion.qrcodereader.di

import android.content.Context
import androidx.room.Room
import br.com.alaksion.qrcodereader.data.datasource.QrCodeLocalDataSource
import br.com.alaksion.qrcodereader.data.local.Database
import br.com.alaksion.qrcodereader.data.local.QrCodeLocalDataSourceImpl
import br.com.alaksion.qrcodereader.data.repository.QrCodeRepositoryImpl
import br.com.alaksion.qrcodereader.domain.repository.QrCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDI {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context,
            Database::class.java, "qr-database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocalDataSource(database: Database): QrCodeLocalDataSource {
        return QrCodeLocalDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideRepository(dataSource: QrCodeLocalDataSource): QrCodeRepository {
        return QrCodeRepositoryImpl(dataSource)
    }

}