package br.com.alaksion.qrcodereader.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import com.google.zxing.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

internal sealed class QrReaderVmEvents {
    data class NavigateToSuccess(val scanResult: Result) : QrReaderVmEvents()
}

@HiltViewModel
internal class QrReaderViewModel @Inject constructor(
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _events = MutableSharedFlow<QrReaderVmEvents>()
    val events = _events.asSharedFlow()

    fun onScanSuccess(result: Result) {
        viewModelScope.launch(dispatcher) {
            _events.emit(QrReaderVmEvents.NavigateToSuccess(result))
        }
    }

}