package br.com.alaksion.qrcodereader.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuccessVmEvents {
    data class SaveScanSuccess(val savedScan: Scan) : SuccessVmEvents()
    object CloseScan : SuccessVmEvents()
}

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<SuccessVmEvents>()
    val events = _events.asSharedFlow()

    private val _scanTitle = MutableStateFlow("New Scan")
    val scanTitle = _scanTitle.asStateFlow()

    private val _isSaveButtonEnabled = MutableStateFlow(true)
    val isSaveButtonEnabled = _isSaveButtonEnabled.asStateFlow()

    fun saveScan(
        code: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.storeScan(
                CreateScanRequest(
                    code = code,
                    title = _scanTitle.value
                )
            ).collectLatest {
                _events.emit(SuccessVmEvents.SaveScanSuccess(it))
            }
        }
    }

    fun closeScan() {
        viewModelScope.launch(Dispatchers.IO) {
            _events.emit(SuccessVmEvents.CloseScan)
        }
    }

    fun onChangeTitle(value: String) {
        _scanTitle.value = value
        _isSaveButtonEnabled.value = value.isNotEmpty()
    }

}