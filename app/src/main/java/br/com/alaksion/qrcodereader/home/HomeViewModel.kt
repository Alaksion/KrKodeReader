package br.com.alaksion.qrcodereader.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: DatabaseRepository
) : ViewModel() {

    private val _scans = MutableStateFlow<HomeScreenState>(HomeScreenState.Loading)
    val scans = _scans.asStateFlow()

    init {
        getScans()
    }

    private fun getScans() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.listScans().collect { scansList ->
                _scans.value =
                    if (scansList.isEmpty()) HomeScreenState.Empty
                    else HomeScreenState.Ready(scansList)
            }
        }
    }

    fun notifyScanRegistered(scan: Scan) {
        when (val currentState = _scans.value) {
            is HomeScreenState.Ready -> {
                val newList = mutableListOf<Scan>()
                newList.addAll(currentState.scans)
                newList.add(scan)
                _scans.value = HomeScreenState.Ready(newList)
            }
            is HomeScreenState.Loading, is HomeScreenState.Empty -> {
                _scans.value = HomeScreenState.Ready((listOf(scan)))
            }
        }
    }

}