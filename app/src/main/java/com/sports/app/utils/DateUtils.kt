package com.sports.app.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    val T_Z_FORMAT: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    val DD_MMM_FORMAT: String = "dd MMM, yyyy"
    val HH_MM_FORMAT: String = "HH:mm"
    val HH_MM_FORMAT_2: String = "HH:mm dd MMM, yyyy"

    fun Date.toFormattedString(
        outputFormat: String,
        locale: Locale = Locale.getDefault(),
        timeZone: TimeZone? = null,
    ): String {
        val formatter = SimpleDateFormat(outputFormat, locale)
        timeZone?.let {
            formatter.timeZone = timeZone
        }
        return formatter.format(this)
    }

    fun String.toDate(
        inputFormat: String = T_Z_FORMAT,
        locale: Locale = Locale.getDefault(),
        timeZone: TimeZone? = null,
    ): Date? {
        val parser = SimpleDateFormat(inputFormat, locale)
        timeZone?.let {
            parser.timeZone = timeZone
        }
        return try {
            parser.parse(this)
        } catch (e: Exception) {
            null
        }
    }
}
