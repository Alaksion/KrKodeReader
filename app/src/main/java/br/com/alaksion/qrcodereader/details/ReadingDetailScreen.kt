package br.com.alaksion.qrcodereader.details

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.components.LoadingScreen
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    route = "/details"
)
@Composable
@ExperimentalMaterialApi
fun ReadingDetailScreen(
    scanId: Long,
    navigator: DestinationsNavigator
) {
    val viewModel: ReadingDetailsViewModel = readingDetailsViewModel(scanId = scanId)

    ReadingDetailsContent(
        screenState = viewModel.scanDetailsState.collectAsState().value
    )
}

@Composable
internal fun ReadingDetailsContent(
    screenState: ReadingDetailState
) {
    when (screenState) {
        is ReadingDetailState.Loading -> Scaffold { LoadingScreen() }
        is ReadingDetailState.Ready -> ReadingDetailsReady(scan = screenState.scan)
    }

}

@Composable
internal fun ReadingDetailsReady(scan: Scan) {
    Scaffold {
        Text(scan.title)
    }
}