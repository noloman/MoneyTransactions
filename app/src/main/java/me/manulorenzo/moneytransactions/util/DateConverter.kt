package me.manulorenzo.moneytransactions.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import java.text.ParseException

class DateConverter {
    fun toDate(dateString: String): LocalDateTime {
        return try {
            LocalDateTime.parse(
                dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssX")
            )
        } catch (e: ParseException) {
            // TODO
            LocalDateTime.now()
        }
    }
}