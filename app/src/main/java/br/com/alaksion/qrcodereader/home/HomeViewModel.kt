package br.com.alaksion.qrcodereader.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import br.com.alaksion.core_platform_utils.dispatchersmodule.IoDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val repository: DatabaseRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _homeState = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val homeState = _homeState.asStateFlow()

    private val scans = MutableStateFlow<MutableList<Scan>>(mutableListOf())

    init {
        getScans()
    }

    private fun getScans() {
        viewModelScope.launch(ioDispatcher) {
            repository.listScans().collectLatest { scansList ->
                _homeState.value =
                    if (scansList.isEmpty()) HomeScreenState.Empty
                    else {
                        scans.value.addAll(scansList)
                        HomeScreenState.Ready(scans.value)
                    }
            }
        }
    }

    fun notifyScanRegistered(scan: Scan) {
        val newList = scans.value.apply {
            add(scan)
        }
        _homeState.value = HomeScreenState.Ready(newList)
    }

    fun onDeleteScan(scan: Scan) {
        repository.deleteScan(scan)
        val newList = scans.value.apply {
            remove(scan)
        }
        _homeState.value =
            if (newList.isEmpty()) HomeScreenState.Empty
            else HomeScreenState.Ready(newList)
    }

}