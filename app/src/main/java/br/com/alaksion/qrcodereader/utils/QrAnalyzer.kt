package br.com.alaksion.qrcodereader.utils

import android.graphics.ImageFormat.YUV_420_888
import android.graphics.ImageFormat.YUV_422_888
import android.graphics.ImageFormat.YUV_444_888
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.alaksion.core_utils.extensions.toByteArray
import com.google.zxing.BarcodeFormat
import com.google.zxing.BinaryBitmap
import com.google.zxing.DecodeHintType
import com.google.zxing.MultiFormatReader
import com.google.zxing.NotFoundException
import com.google.zxing.PlanarYUVLuminanceSource
import com.google.zxing.Result
import com.google.zxing.common.HybridBinarizer

class QrAnalyzer(
        private val onCodeAnalyzed: (Result) -> Unit,
        private val onInvalidFormat: () -> Unit
) : ImageAnalysis.Analyzer {

    // Since minSdk is 24 all the formats below are accepted by default
    private val yuvFormats = mutableListOf(YUV_420_888, YUV_422_888, YUV_444_888)

    private val reader = MultiFormatReader().apply {
        val map = mapOf(
                DecodeHintType.POSSIBLE_FORMATS to arrayListOf(BarcodeFormat.QR_CODE)
        )
        setHints(map)
    }

    override fun analyze(image: ImageProxy) {
        if (image.format !in yuvFormats) {
            onInvalidFormat()
            return
        }
        val data = image.planes.first().buffer.toByteArray()
        val source = PlanarYUVLuminanceSource(
                data,
                image.width,
                image.height,
                0,
                0,
                image.width,
                image.height,
                false
        )

        val binaryBitmap = BinaryBitmap(HybridBinarizer(source))
        try {
            val result = reader.decode(binaryBitmap)
            onCodeAnalyzed(result)
        } catch (e: NotFoundException) {
            // TODO -> Replace with logger
            print(e.localizedMessage)
        }
        image.close()
    }

}
