package br.com.alaksion.qrcodereader.details

import app.cash.turbine.test
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

internal class ReadingDetailsViewModelTest {

    private val repository = mockk<DatabaseRepository>()
    private val dispatcher = TestCoroutineDispatcher()
    private val scanId = 1L

    private lateinit var viewModel: ReadingDetailsViewModel

    @Before
    fun setUp() {
        coEvery { repository.getScan(any()) } returns
                flow { emit(ReadingDetailsVmTestData.scan) }

        viewModel = ReadingDetailsViewModel(
            repository = repository,
            dispatcher = dispatcher,
            scanId = scanId
        )
    }

    @Test
    fun `Should load scan from repository`() = runBlocking {
        val state = viewModel.scanDetailsState

        Truth.assertThat(state.value).isInstanceOf(ReadingDetailState.Ready::class.java)

        coVerify(exactly = 1) { repository.getScan(scanId) }
    }

    @Test
    fun `Should emit deleted event on scan delete`() = runBlocking {
        coEvery { repository.deleteScan(any()) } returns Unit

        viewModel.events.test {
            viewModel.deleteScan(ReadingDetailsVmTestData.scan)

            val event = awaitItem()

            Truth.assertThat(event)
                .isInstanceOf(ReadingDetailsEvents.ScanDeleted::class.java)
        }
    }

    @Test
    fun `Should call repository on scan delete`() = runBlocking {
        coEvery { repository.deleteScan(any()) } returns Unit

        viewModel.deleteScan(ReadingDetailsVmTestData.scan)

        coVerify(exactly = 1) { repository.deleteScan(ReadingDetailsVmTestData.scan) }
    }

}