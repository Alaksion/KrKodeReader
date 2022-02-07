package br.com.alaksion.qrcodereader.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

fun Date.formatCurrentDate(): String {
    val sdf = SimpleDateFormat("dd/M/yyyy")
    return sdf.format(this)
}