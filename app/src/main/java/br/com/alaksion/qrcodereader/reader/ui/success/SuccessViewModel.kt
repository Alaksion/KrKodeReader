package br.com.alaksion.qrcodereader.reader.ui.success

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.model.CreateScanRequest
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SuccessVmEvents {
    object SaveScanSuccess : SuccessVmEvents()
    object CloseScan : SuccessVmEvents()
}

@HiltViewModel
class SuccessViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    private val _events = MutableSharedFlow<SuccessVmEvents>()
    val events = _events.asSharedFlow()

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

    fun closeScan() {
        viewModelScope.launch {
            _events.emit(SuccessVmEvents.CloseScan)
        }
    }

}