package br.com.alaksion.qrcodereader.success

import app.cash.turbine.test
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

internal class SuccessViewModelTest {

    private lateinit var viewModel: SuccessViewModel
    private val dispatcher = TestCoroutineDispatcher()
    private val repository = mockk<DatabaseRepository>()

    @Before
    fun setUp() {
        viewModel = SuccessViewModel(repository, dispatcher)
    }

    @Test
    fun `should update scan title on change`() {
        viewModel.onChangeTitle("teste")

        Truth.assertThat(viewModel.scanTitle.value).isEqualTo("teste")
    }

    @Test
    fun `should emit cloe scan event when scan is closed`() = runBlocking {
        viewModel.events.test {

            viewModel.closeScan()
            val event = awaitItem()

            Truth.assertThat(event).isInstanceOf(SuccessVmEvents.CloseScan::class.java)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `should not save scan when scan is closed`() {
        viewModel.closeScan()

        coVerify(inverse = true) { repository.storeScan(any()) }
        confirmVerified(repository)
    }

    @Test
    fun `should call repository when scan is saved`() = runBlocking {
        coEvery { repository.storeScan(any()) } returns
                flow { emit(SuccessVmTestData.scanResponse) }

        viewModel.onChangeTitle("title")
        viewModel.saveScan("code")

        coVerify(exactly = 1) { repository.storeScan(any()) }
        confirmVerified(repository)

    }

    @Test
    fun `should emit scan saved event when scan is saved`() = runBlocking {
        coEvery { repository.storeScan(any()) } returns
                flow { emit(SuccessVmTestData.scanResponse) }

        viewModel.events.test {
            viewModel.onChangeTitle("title")
            viewModel.saveScan("code")

            val event = expectMostRecentItem()

            Truth.assertThat(event)
                .isInstanceOf(SuccessVmEvents.SaveScanSuccess::class.java)

            Truth.assertThat((event as SuccessVmEvents.SaveScanSuccess).savedScan)
                .isEqualTo(SuccessVmTestData.scanResponse)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `Should disable save button when title is empty`() {
        viewModel.onChangeTitle("")

        Truth.assertThat(viewModel.isSaveButtonEnabled.value).isFalse()
    }

    @Test
    fun `Should enable save button when title is filled`() {
        viewModel.onChangeTitle("TITLE")

        Truth.assertThat(viewModel.isSaveButtonEnabled.value).isTrue()
    }
}