package br.com.alaksion.qrcodereader.details.components

import android.graphics.Bitmap
import android.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import br.com.alaksion.core_ui.providers.dimensions.LocalDimesions
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter

@Composable
fun ReadingQrCode(
    modifier: Modifier = Modifier,
    code: String
) {
    val dimensions = LocalDimesions.current
    val bitmap = generateBitmap(code)

    bitmap?.let {
        Box(
            modifier = modifier
                .border(
                    border = BorderStroke(
                        width = 20.dp, brush = Brush.linearGradient(
                            colors = listOf(
                                MaterialTheme.colors.primaryVariant,
                                MaterialTheme.colors.primary,
                            )
                        )
                    ),
                    shape = MaterialTheme.shapes.large
                )
        ) {
            Image(
                modifier = Modifier.padding(dimensions.Padding.medium),
                bitmap = bitmap.asImageBitmap(),
                contentDescription = ""
            )
        }
    }
}

internal fun generateBitmap(code: String): Bitmap? {
    val writer = QRCodeWriter()
    return try {
        val bitMatrix = writer.encode(code, BarcodeFormat.QR_CODE, 600, 600)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp.setPixel(x, y, if (bitMatrix[x, y]) Color.BLACK else Color.WHITE)
            }
        }
        bmp
    } catch (e: WriterException) {
        e.printStackTrace()
        null
    }
}