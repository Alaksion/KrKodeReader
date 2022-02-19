package br.com.alaksion.qrcodereader.details

import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = "/details"
)
fun ReadingDetailScreen(
    scanId: Int,
    navigator: DestinationsNavigator
) {
    ReadingDetailsContent()
}

internal fun ReadingDetailsContent() {

}