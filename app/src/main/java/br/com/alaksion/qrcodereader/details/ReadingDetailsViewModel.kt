package br.com.alaksion.qrcodereader.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import br.com.alaksion.core_ui.providers.activity.GetActivity
import br.com.alaksion.qrcodereader.MainActivity
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ReadingDetailsViewModel @AssistedInject constructor(
    private val repository: DatabaseRepository,
    @Assisted private val scanId: Long,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _scanDetailsState = MutableStateFlow<ReadingDetailState>(ReadingDetailState.Loading)
    val scanDetailsState = _scanDetailsState.asStateFlow()

    init {
        viewModelScope.launch(dispatcher) {
            repository.getScan(scanId).collectLatest {
                _scanDetailsState.value = ReadingDetailState.Ready(it)
            }
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
interface ReadingDetailsVmFactory {
    fun create(scanId: Long): ReadingDetailsViewModel
}

@ExperimentalMaterialApi
@Composable
fun readingDetailsViewModel(scanId: Long): ReadingDetailsViewModel {
    val factory = EntryPointAccessors.fromActivity(
        GetActivity<MainActivity>(),
        MainActivity.AssistedFactoryProvider::class.java
    ).readingDetailsViewModel()

    return viewModel(factory = ReadingDetailsViewModel.provideFactory(factory, scanId))
}