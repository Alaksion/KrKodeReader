package br.com.alaksion.qrcodereader.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.components.LoadingScreen
import br.com.alaksion.core_ui.components.Separator
import br.com.alaksion.core_ui.providers.activity.GetActivity
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.details.components.*
import br.com.alaksion.qrcodereader.home.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Destination(
    route = "/details"
)
@Composable
@ExperimentalMaterialApi
internal fun ReadingDetailScreen(
    scanId: Long,
    navigator: DestinationsNavigator
) {
    val viewModel: ReadingDetailsViewModel = readingDetailsViewModel(scanId = scanId)
    val homeViewModel: HomeViewModel = hiltViewModel(GetActivity())
    val clipBoard = LocalClipboardManager.current

    ReadingDetailsContent(
        screenState = viewModel.scanDetailsState.collectAsState().value,
        onBackClick = { navigator.popBackStack() },
        onDeleteClick = { viewModel.deleteScan(it) },
        onCopyClick = { clipBoard.setText(AnnotatedString(it)) },
    )

    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is ReadingDetailsEvents.ScanDeleted -> {
                    homeViewModel.notifyScanDeleted(event.item)
                    navigator.popBackStack()
                }
            }
        }
    }

}

@Composable
@ExperimentalMaterialApi
internal fun ReadingDetailsContent(
    screenState: ReadingDetailState,
    onBackClick: () -> Unit,
    onDeleteClick: (Scan) -> Unit,
    onCopyClick: (String) -> Unit
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
                onBackClick,
                onDeleteClick,
                onCopyClick,
            )
    }
}

@ExperimentalMaterialApi
@Composable
internal fun ReadingDetailsReady(
    scan: Scan,
    onBackClick: () -> Unit,
    onDeleteClick: (Scan) -> Unit,
    onCopyClick: (String) -> Unit,
) {
    val dimensions = LocalDimesions.current
    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        topBar = {
            ReadingDetailsTopBar(onBackArrowClick = onBackClick)
        },
        sheetContent = {
            DeleteReadingBottomSheet(
                dissmissClick = { scope.launch { sheetState.bottomSheetState.collapse() } },
                onConfirmDeleteClick = { onDeleteClick(scan) }
            )
        },
        sheetShape = RoundedCornerShape(
            topStart = dimensions.Padding.small,
            topEnd = dimensions.Padding.small,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        sheetPeekHeight = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensions.Padding.small),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Separator(height = dimensions.Separators.medium)
            Text(
                text = scan.title,
                style = MaterialTheme.typography.h5.copy(
                    fontWeight = FontWeight.Bold
                )
            )
            Separator(height = dimensions.Separators.medium)
            ReadingQrCode(
                code = scan.code
            )
            Spacer(Modifier.height(dimensions.Separators.large))
            ReadingDetailsActions(
                modifier = Modifier
                    .fillMaxWidth(),
                onClickCopy = { onCopyClick(scan.code) },
                onCLickDelete = { scope.launch { sheetState.bottomSheetState.expand() } },
            )
            Spacer(Modifier.height(dimensions.Separators.medium))
            Text(
                text = stringResource(id = R.string.your_code_stands_for),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimensions.Separators.small))
            ReadingDetailsCodeValue(
                code = scan.code,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimensions.Padding.small))
        }
    }
}