package br.com.alaksion.qrcodereader.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.components.LoadingScreen
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.core_utils.extensions.isEven
import br.com.alaksion.qrcodereader.MainActivity
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.destinations.QrReaderDestination
import br.com.alaksion.qrcodereader.destinations.ReadingDetailScreenDestination
import br.com.alaksion.qrcodereader.home.components.HomeFab
import br.com.alaksion.qrcodereader.home.components.ScanCard
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@ExperimentalMaterialApi
@Destination(
    start = true,
    route = "/"
)
@Composable
internal fun HomeScreen(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current as MainActivity

    val viewModel = hiltViewModel<HomeViewModel>(context)

    HomeScreenContent(
        screenState = viewModel.homeState.collectAsState().value,
        onClickNewScan = { navigator.navigate(QrReaderDestination) },
        onClickDelete = { viewModel.onDeleteScan(it) },
        onClickScanDetails = { navigator.navigate(ReadingDetailScreenDestination(it)) }
    )
}

@Composable
internal fun HomeScreenContent(
    screenState: HomeScreenState,
    onClickNewScan: () -> Unit,
    onClickDelete: (Scan) -> Unit,
    onClickScanDetails: (Long) -> Unit
) {
    when (screenState) {
        is HomeScreenState.Ready -> HomeScreenReady(
            items = screenState.scans,
            onClickNewScan = onClickNewScan,
            onClickDelete = onClickDelete,
            onClickScanDetails = onClickScanDetails
        )
        is HomeScreenState.Loading -> Scaffold { LoadingScreen() }
        is HomeScreenState.Empty -> HomeScreenEmpty(onClickNewScan)
    }
}

@Composable
internal fun HomeScreenReady(
    items: List<Scan>,
    onClickNewScan: () -> Unit,
    onClickDelete: (Scan) -> Unit,
    onClickScanDetails: (Long) -> Unit
) {
    val dimesions = LocalDimesions.current

    Scaffold(
        floatingActionButton = {
            HomeFab(onClick = { onClickNewScan() })
        },
    ) {
        val listState = rememberLazyListState()
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = dimesions.Padding.small),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(dimesions.Separators.medium)
        ) {
            item {
                Spacer(Modifier.height(dimesions.Separators.large))
                Text(
                    text = stringResource(id = R.string.home_title),
                    style = MaterialTheme.typography.h5.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(Modifier.height(dimesions.Separators.medium))
            }
            itemsIndexed(items) { index, item ->
                val color =
                    if (index.isEven()) MaterialTheme.colors.primaryVariant
                    else MaterialTheme.colors.secondaryVariant
                ScanCard(
                    scan = item,
                    cardColor = color,
                    onCardClick = onClickScanDetails,
                    onDeleteClick = onClickDelete
                )
            }
        }
    }
}

@Composable
internal fun HomeScreenEmpty(
    onClickNewScan: () -> Unit
) {
    val dimensions = LocalDimesions.current

    Scaffold(
        floatingActionButton = { HomeFab(onClick = { onClickNewScan() }) }
    ) {
        Column(
            modifier = Modifier
                .padding(dimensions.Padding.small)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_empty_list),
                contentDescription = null
            )
            Spacer(Modifier.height(dimensions.Separators.medium))
            Text(
                text = stringResource(id = R.string.home_empty_title),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimensions.Separators.small))
            Text(
                text = stringResource(id = R.string.home_empty_description),
                style = MaterialTheme.typography.body2.copy(
                    color = DarkGrey,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}