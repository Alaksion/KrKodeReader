package br.com.alaksion.qrcodereader.success

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
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
import br.com.alaksion.core_ui.providers.LocalDimesions
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import br.com.alaksion.qrcodereader.MainActivity
import br.com.alaksion.qrcodereader.R
import br.com.alaksion.qrcodereader.destinations.HomeScreenDestination
import br.com.alaksion.qrcodereader.home.HomeViewModel
import br.com.alaksion.qrcodereader.success.components.ScanResultText
import br.com.alaksion.qrcodereader.success.components.ScanSaveBottomSheet
import com.google.accompanist.insets.imePadding
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@Destination(
    route = "/success"
)
@Composable
fun ReadSuccess(
    id: Int,
    code: String,
    navigator: DestinationsNavigator
) {
    val hostActivity = LocalContext.current as MainActivity

    val viewModel = hiltViewModel<SuccessViewModel>()
    val homeViewModel = hiltViewModel<HomeViewModel>(hostActivity)

    fun goToHome() {
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

    LaunchedEffect(key1 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is SuccessVmEvents.SaveScanSuccess -> {
                    goToHome()
                    homeViewModel.notifyScanRegistered()
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
fun ReadSuccessContent(
    code: String,
    onClickSave: () -> Unit,
    onClickClose: () -> Unit,
    onChangeScanTitle: (String) -> Unit,
    scanTitle: String,
    isButtonEnabled: Boolean,
) {
    val scope = rememberCoroutineScope()
    val dimesions = LocalDimesions.current
    val scrollState = rememberScrollState()
    val clipManager = LocalClipboardManager.current
    val scaffoldState = rememberScaffoldState()
    val bottomsheetState =
        rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

    fun copyToClipboard(value: String) {
        clipManager.setText(AnnotatedString(value))
    }

    ModalBottomSheetLayout(
        modifier = Modifier.imePadding(),
        sheetShape = RoundedCornerShape(
            topStart = dimesions.paddingSmall,
            topEnd = dimesions.paddingSmall,
            bottomEnd = 0.dp,
            bottomStart = 0.dp
        ),
        sheetState = bottomsheetState,
        sheetContent = {
            ScanSaveBottomSheet(
                onChangeScanTitle = onChangeScanTitle,
                onClickSave = onClickSave,
                scanTitle = scanTitle,
                isSaveButtonEnabled = isButtonEnabled,
                sheetState = bottomsheetState,
            )
        },
        content = {
            Scaffold(
                scaffoldState = scaffoldState,
            ) {
                ConstraintLayout(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = dimesions.paddingMedium)
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
                            painter = painterResource(id = R.drawable.ic_check_outline),
                            contentDescription = null,
                            modifier = Modifier
                                .size(200.dp),
                            tint = MaterialTheme.colors.primary
                        )
                        Spacer(modifier = Modifier.height(dimesions.paddingMedium))
                        Text(
                            text = stringResource(id = R.string.read_success),
                            style = MaterialTheme.typography.h4.copy(
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                        )
                        Spacer(modifier = Modifier.height(dimesions.paddingSmall))
                        Text(
                            text = stringResource(id = R.string.read_success_description),
                            style = MaterialTheme.typography.body1.copy(
                                textAlign = TextAlign.Center,
                            )
                        )
                        Spacer(modifier = Modifier.height(dimesions.paddingSmall))
                        ScanResultText(
                            scrollState = scrollState,
                            text = code,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimesions.paddingMedium),
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
                    ) {
                        Button(
                            onClick = { scope.launch { bottomsheetState.show() } },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = dimesions.paddingSmall)
                                .height(dimesions.acessibilityHeight)

                        ) {
                            Text(stringResource(id = R.string.save_reading))
                        }
                        TextButton(
                            onClick = { onClickClose() },
                            modifier = Modifier
                                .height(dimesions.acessibilityHeight)
                                .fillMaxWidth()
                        ) {
                            Text(stringResource(id = R.string.text_close))
                        }
                    }
                }
            }
        }
    )

}

@Composable
@ExperimentalMaterialApi
@Preview
fun ReadSuccessPreview() {
    QrCodeReaderTheme() {
        ReadSuccessContent("code", {}, {}, {}, "", true)
    }
}