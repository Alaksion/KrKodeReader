package br.com.alaksion.core_db.di

import android.content.Context
import androidx.room.Room
import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.local.Database
import br.com.alaksion.core_db.data.local.DatabaseDataSourceImpl
import br.com.alaksion.core_db.data.repository.DatabaseRepositoryImpl
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseDI {

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
    fun provideLocalDataSource(database: Database): DatabaseDataSource {
        return DatabaseDataSourceImpl(database)
    }

    @Provides
    @Singleton
    fun provideRepository(dataSource: DatabaseDataSource): DatabaseRepository {
        return DatabaseRepositoryImpl(dataSource)
    }

}