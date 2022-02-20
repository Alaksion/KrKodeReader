package br.com.alaksion.qrcodereader.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.components.LoadingScreen
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.qrcodereader.details.components.ReadingDetailsTopBar
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
        screenState = viewModel.scanDetailsState.collectAsState().value,
        onBackClick = { navigator.popBackStack() }
    )
}

@Composable
internal fun ReadingDetailsContent(
    screenState: ReadingDetailState,
    onBackClick: () -> Unit
) {
    when (screenState) {
        is ReadingDetailState.Loading ->
            Scaffold(
                topBar = { ReadingDetailsTopBar(onBackArrowClick = onBackClick) }
            ) {
                LoadingScreen()
            }
        is ReadingDetailState.Ready ->
            ReadingDetailsReady(
                scan = screenState.scan,
                onBackClick
            )
    }

}

@Composable
internal fun ReadingDetailsReady(
    scan: Scan,
    onBackClick: () -> Unit
) {
    val dimensions = LocalDimesions.current

    Scaffold(
        topBar = {
            ReadingDetailsTopBar(onBackArrowClick = onBackClick)
        }
    ) {
        Column(
            modifier = Modifier.padding(dimensions.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


        }
    }
}