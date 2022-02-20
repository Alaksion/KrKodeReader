package br.com.alaksion.qrcodereader.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuccessVmEvents {
    data class SaveScanSuccess(val savedScan: Scan) : SuccessVmEvents()
    object CloseScan : SuccessVmEvents()
}

@HiltViewModel
internal class SuccessViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
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
        viewModelScope.launch(dispatcher) {
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
        viewModelScope.launch(dispatcher) {
            _events.emit(SuccessVmEvents.CloseScan)
        }
    }

    fun onChangeTitle(value: String) {
        _scanTitle.value = value
        _isSaveButtonEnabled.value = value.isNotEmpty()
    }

}