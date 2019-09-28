package me.manulorenzo.moneyyoutransaction.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {
    fun toDate(dateString: String): Date? {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
        return dateFormat.parse(dateString)
    }
}