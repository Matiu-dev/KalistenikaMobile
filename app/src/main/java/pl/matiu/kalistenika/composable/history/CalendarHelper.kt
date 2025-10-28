package pl.matiu.kalistenika.composable.history

import java.text.SimpleDateFormat
import java.util.Locale

object CalendarHelper {
    val daysQueue = listOf(2, 3, 4, 5, 6, 7, 1)
    val monthQueue = mapOf(0 to "Styczeń", 1 to "Luty", 2 to "Marzec", 3 to "Kwiecień", 4 to "Maj",
        5 to "Czerwiec", 6 to "Lipiec", 7 to "Sierpień", 8 to "Wrzesień", 9 to "Październik", 10 to "Listopad",
        11 to "Grudzień")
    val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
}