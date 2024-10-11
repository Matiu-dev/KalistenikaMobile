package pl.matiu.kalistenika.ui.history

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HistoryDetailsScreen(date: String) {

    var dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
    val parsedDate = dateFormat.parse(date)

    val calendar = Calendar.getInstance()
    calendar.time = parsedDate

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Text(text = "Treningi wykonane w dniu: " +
                "${calendar.get(Calendar.DAY_OF_MONTH)}." +
                "${calendar.get(Calendar.MONTH)}." +
                "${calendar.get(Calendar.YEAR)}")
    }
}
