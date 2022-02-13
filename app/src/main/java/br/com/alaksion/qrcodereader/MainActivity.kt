package br.com.alaksion.qrcodereader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import br.com.alaksion.core_ui.providers.DimesionsProvider
import br.com.alaksion.core_ui.theme.QrCodeReaderTheme
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            QrCodeReaderTheme {
                DimesionsProvider {
                    ProvideWindowInsets(windowInsetsAnimationsEnabled = true) {
                        DestinationsNavHost(
                            navGraph = NavGraphs.root,
                            modifier = Modifier.systemBarsPadding()
                        )
                    }
                }
            }
        }
    }
}
