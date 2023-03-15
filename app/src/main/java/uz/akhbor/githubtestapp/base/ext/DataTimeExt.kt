package uz.akhbor.githubtestapp.base.ext

import android.annotation.SuppressLint
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

private const val TAG = "DataTimeExt"
private const val YEAR_MONTH_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"

@SuppressLint("ConstantLocale")
val DEFAULT_LOCALE = Locale.getDefault()

val DEFAULT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ISO_ZONED_DATE_TIME
    .withLocale(DEFAULT_LOCALE)

val YEAR_MONTH_DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(YEAR_MONTH_DATE_TIME_FORMAT)

fun tryParseDateTime(rawDateTime: String?): LocalDateTime? {
    if (rawDateTime.isNullOrEmpty()) {
        Log.d(TAG, "Attempt to parse empty or null string!")
        return null
    }

    val resultDateTimeList = try {
        LocalDateTime.parse(rawDateTime, DEFAULT_DATE_FORMATTER)
    } catch (e: Throwable) {
        null
    }

    return resultDateTimeList ?: run {
        Log.d(TAG, "Unable to parse rawDateTime ($rawDateTime)")
        null
    }
}
