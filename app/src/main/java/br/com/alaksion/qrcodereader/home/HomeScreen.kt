package br.com.alaksion.qrcodereader.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.LightGrey
import br.com.alaksion.core_ui.theme.Orange
import br.com.alaksion.core_utils.extensions.isEven
import br.com.alaksion.qrcodereader.MainActivity
import br.com.alaksion.qrcodereader.destinations.QrReaderDestination
import br.com.alaksion.qrcodereader.home.components.ScanCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@Destination(
    start = true,
    route = "/"
)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current as MainActivity

    val viewModel = hiltViewModel<HomeViewModel>(context)

    HomeScreenContent(
        screenState = viewModel.scans.collectAsState().value,
        onClickNewScan = { navigator.navigate(QrReaderDestination) }
    )
}

@Composable
internal fun HomeScreenContent(
    screenState: HomeScreenState,
    onClickNewScan: () -> Unit
) {
    when (screenState) {
        is HomeScreenState.Ready -> HomeScreenReady(
            items = screenState.scans,
            onClickNewScan = onClickNewScan
        )
        is HomeScreenState.Loading -> HomeScreenLoading()
        is HomeScreenState.Empty -> HomeScreenEmpty()
    }
}

@Composable
fun HomeScreenReady(
    items: List<Scan>,
    onClickNewScan: () -> Unit
) {
    val dimesions = LocalDimesions.current

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onClickNewScan() }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimesions.Padding.medium),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(dimesions.Separators.medium)
        ) {
            itemsIndexed(items) { index, item ->
                val color = if (index.isEven()) Orange.copy(0.5f) else LightGrey.copy(0.5f)
                ScanCard(
                    scan = item,
                    cardColor = color,
                    onCardClick = {},
                    onDeleteClick = {}
                )
            }
        }
    }
}

@Composable
fun HomeScreenLoading() {
    val dimesions = LocalDimesions.current

    Scaffold() {
        Box(
            modifier = Modifier.padding(horizontal = dimesions.Padding.medium),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun HomeScreenEmpty() {
    Scaffold() {
        Text("No scans")
    }
}