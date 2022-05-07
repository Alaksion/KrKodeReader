package br.com.alaksion.qrcodereader.details

import br.com.alaksion.core_db.domain.model.Scan

internal sealed class ReadingDetailState {
    object Loading : ReadingDetailState()
    data class Ready(val scan: Scan) : ReadingDetailState()
}
