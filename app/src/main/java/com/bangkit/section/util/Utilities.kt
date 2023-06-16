package com.bangkit.section.util

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

object Utilities {
    fun formatTimeDifference(dateTime: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("GMT") // Mengatur zona waktu ke GMT
        val currentTime = Calendar.getInstance()
        currentTime.timeZone = TimeZone.getTimeZone("Asia/Jakarta") // Mengatur zona waktu ke WIB
        val givenTime = dateFormat.parse(dateTime)

        val differenceInMillis = currentTime.timeInMillis - givenTime.time
        val seconds = differenceInMillis / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24
        val weeks = days / 7

        return when {
            weeks >= 1 -> dateFormat.format(givenTime)
            days >= 1 -> "${days} hari"
            hours >= 1 -> "${hours} jam"
            minutes >= 1 -> "${minutes} menit"
            else -> "Baru saja"
        }
    }
}