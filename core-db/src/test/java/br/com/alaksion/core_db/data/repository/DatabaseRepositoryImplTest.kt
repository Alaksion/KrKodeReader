package br.com.alaksion.core_db.data.repository

import br.com.alaksion.core_db.data.datasource.DatabaseDataSource
import br.com.alaksion.core_db.data.model.mapToScan
import br.com.alaksion.core_db.data.repository.testdata.DatabaseRepositoryTestData
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

internal class DatabaseRepositoryImplTest {

    private val dispatcher = TestCoroutineDispatcher()
    private val dataSource = mockk<DatabaseDataSource>()

    private lateinit var repository: DatabaseRepository

    @Before
    fun setUp() {
        repository = DatabaseRepositoryImpl(
            dispatcher = dispatcher,
            localDataSource = dataSource
        )
    }

    @Test
    fun `should store scan in data source layer`() = runBlocking {
        coEvery { dataSource.storeScan(any()) } returns
                flow { emit(DatabaseRepositoryTestData.scanData) }

        val response = repository.storeScan(DatabaseRepositoryTestData.createScanRequest).first()

        Truth.assertThat(response).isEqualTo(DatabaseRepositoryTestData.scan)
        coVerify(exactly = 1) { dataSource.storeScan(DatabaseRepositoryTestData.scanData) }
        confirmVerified(dataSource)
    }

    @Test
    fun `should get scans from data source layer`() = runBlocking {
        coEvery { dataSource.getScans() } returns flow { emit(DatabaseRepositoryTestData.scans) }

        val response = repository.listScans().first()

        Truth.assertThat(response)
            .isEqualTo(DatabaseRepositoryTestData.scans.map { it -> it.mapToScan() })
        coVerify(exactly = 1) { dataSource.getScans() }
        confirmVerified(dataSource)
    }

    @Test
    fun `should delete scan in data source layer`() = runBlocking {
        coEvery { dataSource.deleteScan(any()) } returns Unit

        repository.deleteScan(DatabaseRepositoryTestData.scan)

        coVerify(exactly = 1) { dataSource.deleteScan(any()) }
        confirmVerified(dataSource)
    }

    @Test
    fun `should get scan from data source layer`() = runBlocking {
        coEvery { dataSource.getScan(1) } returns
                flow { emit(DatabaseRepositoryTestData.scanData) }

        val response = repository.getScan(1).first()

        Truth.assertThat(response).isEqualTo(DatabaseRepositoryTestData.scan)
        coVerify(exactly = 1) { dataSource.getScan(1) }
        confirmVerified(dataSource)
    }

}