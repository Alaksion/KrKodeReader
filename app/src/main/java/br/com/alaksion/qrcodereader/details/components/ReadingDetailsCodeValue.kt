package br.com.alaksion.qrcodereader.details.components

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import br.com.alaksion.core_ui.theme.DarkGrey
import br.com.alaksion.core_ui.theme.LightGrey

@Composable
internal fun ReadingDetailsCodeValue(
    modifier: Modifier = Modifier,
    code: String,
    textScrollState: ScrollState
) {
    val dimensions = LocalDimesions.current
    Text(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .background(LightGrey)
            .padding(
                horizontal = dimensions.Padding.medium,
                vertical = dimensions.Padding.small
            )
            .verticalScroll(textScrollState),
        text = code,
        style = MaterialTheme.typography.body1.copy(
            fontWeight = FontWeight.Bold,
            color = DarkGrey
        )
    )

}