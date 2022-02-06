package br.com.alaksion.qrcodereader.ui.reader

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.zxing.Result
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

sealed class QrReaderVmEvents {
    data class NavigateToSuccess(val scanResult: Result) : QrReaderVmEvents()
}

class QrReaderViewModel : ViewModel() {

    private val _events = MutableSharedFlow<QrReaderVmEvents>()
    val events = _events.asSharedFlow()

    fun onScanSuccess(result: Result) {
        viewModelScope.launch {
            _events.emit(QrReaderVmEvents.NavigateToSuccess(result))
        }
    }

}