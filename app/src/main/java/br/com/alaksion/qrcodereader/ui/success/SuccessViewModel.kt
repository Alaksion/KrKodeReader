package br.com.alaksion.qrcodereader.ui.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.qrcodereader.domain.model.CreateScanRequest
import br.com.alaksion.qrcodereader.domain.repository.QrCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuccessVmEvents {
    object SaveScanSuccess : SuccessVmEvents()
}

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val repository: QrCodeRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<SuccessVmEvents>()
    val events = _events.asSharedFlow()

    private val _isScanSaved = MutableStateFlow(false)
    val isScanSaved = _isScanSaved.asStateFlow()

    fun saveScan(
        code: String,
        title: String
    ) {
        viewModelScope.launch {
            repository.storeScan(
                CreateScanRequest(
                    code = code,
                    title = title
                )
            )
        }
    }

}