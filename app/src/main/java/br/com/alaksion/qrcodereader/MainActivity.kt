package br.com.alaksion.qrcodereader

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import br.com.alaksion.qrcodereader.ui.NavGraphs
import br.com.alaksion.qrcodereader.ui.theme.QrCodeReaderTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QrCodeReaderTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
