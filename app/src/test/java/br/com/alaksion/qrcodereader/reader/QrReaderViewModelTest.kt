package br.com.alaksion.qrcodereader.reader

import app.cash.turbine.test
import com.google.common.truth.Truth
import com.google.zxing.Result
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Test

internal class QrReaderViewModelTest {

    private lateinit var viewModel: QrReaderViewModel
    private val dispatcher = TestCoroutineDispatcher()
    private val result = mockk<Result>()

    @Before
    fun setUp() {
        viewModel = QrReaderViewModel(dispatcher)
    }

    @Test
    fun `should emit success event when reading succeeds`() = runBlocking {
        viewModel.events.test {
            viewModel.onScanSuccess(result)

            val event = expectMostRecentItem()

            Truth.assertThat(event)
                .isInstanceOf(QrReaderVmEvents.NavigateToSuccess::class.java)

            cancelAndConsumeRemainingEvents()
        }
    }

}