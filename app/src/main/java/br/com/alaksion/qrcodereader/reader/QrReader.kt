package br.com.alaksion.qrcodereader.reader

import android.Manifest
import android.content.pm.PackageManager
import android.util.Size
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
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
fun QrReader(
    navigator: DestinationsNavigator
) {
    val context = LocalContext.current
    val viewModel: QrReaderViewModel = viewModel()

    LaunchedEffect(key1 = navigator, key2 = viewModel) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is QrReaderVmEvents.NavigateToSuccess -> navigator.navigate(
                    ReadSuccessDestination(
                        id = 1,
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

    Scaffold {
        if (hasCameraPermission) QrReaderPermissionGranted { viewModel.onScanSuccess(it) }
        else QrReaderRequestPermission(onUpdateCameraPermission = { hasCameraPermission = it })
    }
}

@Composable
fun QrReaderPermissionGranted(
    onScanSuccess: (Result) -> Unit
) {
    val lifecycle = LocalLifecycleOwner.current
    val context = LocalContext.current
    val cameraProvider = remember { ProcessCameraProvider.getInstance(context) }

    DisposableEffect(key1 = cameraProvider) {
        onDispose {
            cameraProvider.get().unbindAll()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            factory = { context ->
                val previewView = PreviewView(context)
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
                    ContextCompat.getMainExecutor(context),
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
}

@Composable
fun QrReaderRequestPermission(
    onUpdateCameraPermission: (Boolean) -> Unit
) {
    val cameraRequest =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isAllowed ->
            onUpdateCameraPermission(isAllowed)
        }

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