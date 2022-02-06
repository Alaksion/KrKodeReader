package br.com.alaksion.qrcodereader.utils

import android.graphics.ImageFormat.*
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import br.com.alaksion.qrcodereader.utils.extensions.toByteArray
import com.google.zxing.*
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
            e.printStackTrace()
        }
        image.close()
    }

}
