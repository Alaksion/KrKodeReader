package br.com.alaksion.qrcodereader.home

import br.com.alaksion.core_db.domain.model.Scan

sealed class HomeScreenState {
    object Loading : HomeScreenState()
    data class Ready(val scans: List<Scan>) : HomeScreenState()
}