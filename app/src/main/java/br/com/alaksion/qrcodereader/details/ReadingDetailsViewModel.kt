package br.com.alaksion.qrcodereader.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import br.com.alaksion.core_ui.providers.activity.GetActivity
import br.com.alaksion.qrcodereader.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

internal sealed class ReadingDetailsEvents {
    data class ScanDeleted(val item: Scan) : ReadingDetailsEvents()
}

internal class ReadingDetailsViewModel @AssistedInject constructor(
    private val repository: DatabaseRepository,
    @Assisted private val scanId: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _scanDetailsState = MutableStateFlow<ReadingDetailState>(ReadingDetailState.Loading)
    val scanDetailsState = _scanDetailsState.asStateFlow()

    private val _events = Channel<ReadingDetailsEvents> { Channel.BUFFERED }
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch(dispatcher) {
            repository.getScan(scanId).collectLatest {
                _scanDetailsState.value = ReadingDetailState.Ready(it)
            }
        }
    }

    fun deleteScan(scan: Scan) {
        viewModelScope.launch(dispatcher) {
            repository.deleteScan(scan)
            _events.send(ReadingDetailsEvents.ScanDeleted(scan))
        }
    }

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: ReadingDetailsVmFactory,
            scanId: Long
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return assistedFactory.create(scanId) as T
            }
        }
    }

}

@AssistedFactory
internal interface ReadingDetailsVmFactory {
    fun create(scanId: Long): ReadingDetailsViewModel
}

@ExperimentalMaterialApi
@Composable
internal fun readingDetailsViewModel(scanId: Long): ReadingDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        GetActivity<MainActivity>(),
        MainActivity.AssistedFactoryProvider::class.java
    ).readingDetailsViewModel()

    return viewModel(factory = ReadingDetailsViewModel.provideFactory(factory, scanId))
}