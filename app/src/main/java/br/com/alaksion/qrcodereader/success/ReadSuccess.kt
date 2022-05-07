package br.com.alaksion.qrcodereader.success

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import br.com.alaksion.qrcodereader.MainActivity
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.destinations.HomeScreenDestination
import br.com.alaksion.qrcodereader.home.HomeViewModel
import br.com.alaksion.qrcodereader.success.components.ScanResultText
import br.com.alaksion.qrcodereader.success.components.ScanSaveBottomSheet
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import br.com.alaksion.core_ui.R as coreR

@ExperimentalMaterialApi
@Destination(
        route = "/success"
)
@Composable
internal fun ReadSuccess(
        code: String,
        navigator: DestinationsNavigator
) {
    val hostActivity = LocalContext.current as MainActivity

    val viewModel = hiltViewModel<SuccessViewModel>()
    val homeViewModel = hiltViewModel<HomeViewModel>(hostActivity)

    val goToHome: () -> Unit = remember {
        {
            navigator.navigate(
                    direction = HomeScreenDestination,
                    builder = {
                        popUpTo(HomeScreenDestination.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
            )
        }
    }

    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SuccessVmEvents.SaveScanSuccess -> {
                    goToHome()
                    homeViewModel.notifyScanRegistered(event.savedScan)
                }
                is SuccessVmEvents.CloseScan -> {
                    goToHome()
                }
            }
        }
    }

    ReadSuccessContent(
            code = code,
            onClickClose = { viewModel.closeScan() },
            onClickSave = { viewModel.saveScan(code = code) },
            scanTitle = viewModel.scanTitle.collectAsState().value,
            onChangeScanTitle = { viewModel.onChangeTitle(it) },
            isButtonEnabled = viewModel.isSaveButtonEnabled.collectAsState().value
    )

}

@Composable
@ExperimentalMaterialApi
internal fun ReadSuccessContent(
        code: String,
        onClickSave: () -> Unit,
        onClickClose: () -> Unit,
        onChangeScanTitle: (String) -> Unit,
        scanTitle: String,
        isButtonEnabled: Boolean,
) {
    val focus = LocalFocusManager.current
    val scope = rememberCoroutineScope()
    val dimesions = LocalDimesions.current
    val scrollState = rememberScrollState()
    val clipManager = LocalClipboardManager.current
    val scaffoldState = rememberBottomSheetScaffoldState()

    val copyToClipboard: (String) -> Unit = remember {
        {
            clipManager.setText(AnnotatedString(it))
        }
    }
    BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetShape = RoundedCornerShape(
                    topStart = dimesions.Padding.small,
                    topEnd = dimesions.Padding.small,
                    bottomEnd = 0.dp,
                    bottomStart = 0.dp
            ),
            sheetContent = {
                ScanSaveBottomSheet(
                        onChangeScanTitle = onChangeScanTitle,
                        onClickSave = {
                            focus.clearFocus()
                            onClickSave()
                        },
                        scanTitle = scanTitle,
                        isSaveButtonEnabled = isButtonEnabled,
                        dismiss = {
                            scope.launch {
                                scaffoldState.bottomSheetState.collapse()
                            }
                        }
                )
            }
    ) {
        ConstraintLayout(
                modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimesions.Padding.small)
        ) {
            val (content, buttons) = createRefs()

            Column(
                    modifier = Modifier
                            .constrainAs(content) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                top.linkTo(parent.top)
                                bottom.linkTo(buttons.top)
                                height = Dimension.fillToConstraints
                            },
                    horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(50.dp))
                Icon(
                        painter = painterResource(id = coreR.drawable.ic_check_outline),
                        contentDescription = null,
                        modifier = Modifier
                                .size(200.dp),
                        tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(dimesions.Padding.medium))
                Text(
                        text = stringResource(id = R.string.read_success),
                        style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                        )
                )
                Spacer(modifier = Modifier.height(dimesions.Padding.small))
                Text(
                        text = stringResource(id = R.string.read_success_description),
                        style = MaterialTheme.typography.body1.copy(
                                textAlign = TextAlign.Center,
                        )
                )
                Spacer(modifier = Modifier.height(dimesions.Padding.small))
                ScanResultText(
                        scrollState = scrollState,
                        text = code,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimesions.Padding.medium),
                        onCopyToClickBoard = { copyToClipboard(it) }
                )
            }
            Column(
                    modifier = Modifier
                            .fillMaxWidth()
                            .constrainAs(buttons) {
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom)
                            }
                            .padding(bottom = dimesions.Padding.small)
            ) {
                Button(
                        onClick = { scope.launch { scaffoldState.bottomSheetState.expand() } },
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimesions.Padding.small)
                                .height(dimesions.accessibilityHeight)

                ) {
                    Text(stringResource(id = R.string.save_reading))
                }
                TextButton(
                        onClick = { onClickClose() },
                        modifier = Modifier
                                .height(dimesions.accessibilityHeight)
                                .fillMaxWidth()
                ) {
                    Text(stringResource(id = R.string.text_close))
                }
            }
        }
    }

}

@Composable
@ExperimentalMaterialApi
@Preview
fun ReadSuccessPreview() {
    QrCodeReaderTheme() {
        ReadSuccessContent("code", {}, {}, {}, "", true)
    }
}
