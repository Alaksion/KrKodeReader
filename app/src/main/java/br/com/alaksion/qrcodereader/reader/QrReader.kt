package br.com.alaksion.qrcodereader.reader

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.impl.ImageAnalysisConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.AppWhite
import br.com.alaksion.qrcodereader.destinations.ReadSuccessDestination
import br.com.alaksion.qrcodereader.utils.QrAnalyzer
import com.google.zxing.Result
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest

@ExperimentalMaterialApi
@Destination(
    route = "/reader"
)
@Composable
internal fun QrReader(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val viewModel: QrReaderViewModel = hiltViewModel()

    LaunchedEffect(key1 = navigator, key2 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is QrReaderVmEvents.NavigateToSuccess -> navigator.navigate(
                    ReadSuccessDestination(
                        code = event.scanResult.text,
                    ),
                    builder = {
                        popUpTo(ReadSuccessDestination.route) {
                            inclusive = true
                        }
                    }
                )
            }
        }
    }

    var hasCameraPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    if (hasCameraPermission) QrReaderReady(
        onScanSuccess = { viewModel.onScanSuccess(it) },
        onClickClose = { navigator.popBackStack() }
    )
    else QrReaderRequestPermission(onUpdateCameraPermission = { hasCameraPermission = it })
}

@Composable
internal fun QrReaderReady(
    onClickClose: () -> Unit,
    onScanSuccess: (Result) -> Unit
) {
    Scaffold {
        val dimension = LocalDimesions.current

        Box(modifier = Modifier.fillMaxSize()) {
            CameraView(
                modifier = Modifier
                    .fillMaxSize(),
                onScanSuccess = onScanSuccess
            )
            IconButton(onClick = onClickClose) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = null,
                    tint = AppWhite,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(horizontal = dimension.Padding.medium)
                )
            }
        }
    }
}

@Composable
internal fun CameraView(
    modifier: Modifier = Modifier,
    onScanSuccess: (Result) -> Unit
) {
    val context = LocalContext.current
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }
    val lifecycle = LocalLifecycleOwner.current

    DisposableEffect(key1 = cameraProvider) {
        onDispose {
            cameraProvider.get().unbindAll()
        }
    }

    AndroidView(
        modifier = modifier,
        factory = { factoryContext ->
            val previewView = PreviewView(factoryContext)
            val preview = Preview.Builder().build()
            val cameraSelector =
                CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
            preview.setSurfaceProvider(previewView.surfaceProvider)

            val analyzer = ImageAnalysis.Builder()
                .setTargetResolution(
                    Size(
                        previewView.height,
                        previewView.width
                    )
                ).build()

            analyzer.setAnalyzer(
                ContextCompat.getMainExecutor(factoryContext),
                QrAnalyzer(
                    onCodeAnalyzed = { onScanSuccess(it) },
                    onInvalidFormat = {}
                )
            )

            cameraProvider.get().bindToLifecycle(
                lifecycle,
                cameraSelector,
                preview,
                analyzer
            )
            previewView
        }
    )
}

@Composable
internal fun QrReaderRequestPermission(
    onUpdateCameraPermission: (Boolean) -> Unit
) {
    val cameraRequest =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isAllowed ->
            onUpdateCameraPermission(isAllowed)
        }

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Camera permission not available")
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { cameraRequest.launch(Manifest.permission.CAMERA) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text("Request Permission")
            }
        }
    }
}