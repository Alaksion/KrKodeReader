package br.com.alaksion.qrcodereader.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.alaksion.core_db.domain.repository.DatabaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        viewModelScope.launch {
            _scans.value = HomeScreenState.Ready(repository.listScans())
        }
    }

    fun notifyScanRegistered() {
        getScans()
    }

}