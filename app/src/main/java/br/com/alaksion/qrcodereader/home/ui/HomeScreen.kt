package br.com.alaksion.qrcodereader.home.ui

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import br.com.alaksion.qrcodereader.destinations.QrReaderDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(
    start = true,
    route = "/"
)
@Composable
fun HomeScreen(
    navigator: DestinationsNavigator
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(QrReaderDestination) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Text("this is home screen")

    }
}