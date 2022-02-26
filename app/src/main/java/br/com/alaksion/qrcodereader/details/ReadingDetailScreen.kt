package br.com.alaksion.qrcodereader.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import br.com.alaksion.core_db.domain.model.Scan
import br.com.alaksion.core_ui.components.LoadingScreen
import br.com.alaksion.core_ui.components.Separator
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.details.components.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
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
    val clipBoard = LocalClipboardManager.current

    ReadingDetailsContent(
        screenState = viewModel.scanDetailsState.collectAsState().value,
        onBackClick = { navigator.popBackStack() },
        onDeleteClick = {},
        onCopyClick = { clipBoard.setText(AnnotatedString(it)) },
        onSaveClick = {},
        onShareClick = { }
    )
}

@Composable
@ExperimentalMaterialApi
internal fun ReadingDetailsContent(
    screenState: ReadingDetailState,
    onBackClick: () -> Unit,
    onDeleteClick: (Scan) -> Unit,
    onCopyClick: (String) -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit
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
                onShareClick,
                onSaveClick
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
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit
) {
    val dimensions = LocalDimesions.current
    val codeValueScrollState = rememberScrollState()
    val screenScrollState = rememberScrollState()
    val sheetState = rememberBottomSheetScaffoldState()
    val scope = rememberCoroutineScope()

    BottomSheetScaffold(
        scaffoldState = sheetState,
        topBar = {
            ReadingDetailsTopBar(onBackArrowClick = onBackClick)
        },
        sheetContent = {
            DeleteReadingBottomSheet(
                title = scan.title,
                dissmissClick = { scope.launch { sheetState.bottomSheetState.collapse() } },
                onConfirmDeleteClick = { onDeleteClick(scan) }
            )
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimensions.Padding.small)
                .verticalScroll(screenScrollState),
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
            Spacer(Modifier.height(dimensions.Separators.medium))
            ReadingDetailsActions(
                modifier = Modifier
                    .fillMaxWidth(),
                onClickSave = onSaveClick,
                onClickCopy = { onCopyClick(scan.code) },
                onCLickDelete = { scope.launch { sheetState.bottomSheetState.expand() } },
                onClickShare = {}
            )
            Spacer(Modifier.height(dimensions.Separators.medium))
            Text(
                text = stringResource(id = R.string.your_code_stands_for),
                style = MaterialTheme.typography.h6.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(dimensions.Separators.small))
            ReadingDetailsCodeValue(
                code = stringResource(id = R.string.lorem_ipsum),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 200.dp),
                textScrollState = codeValueScrollState
            )
            Spacer(Modifier.height(dimensions.Padding.small))
        }
    }
}