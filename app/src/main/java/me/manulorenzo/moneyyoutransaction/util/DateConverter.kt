package me.manulorenzo.moneyyoutransaction.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DateConverter {
    fun toDate(dateString: String): Date {
        return try {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX", Locale.getDefault())
            // TODO
            dateFormat.parse(dateString)
        } catch (e: ParseException) {
            Date()
        }
    }
}