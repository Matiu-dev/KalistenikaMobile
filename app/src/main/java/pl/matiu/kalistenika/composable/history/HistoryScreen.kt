package pl.matiu.kalistenika.composable.history

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.ui.theme.ZielonyNapis
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Month
import java.util.Calendar
import java.util.Date
import java.util.Locale

//TODO https://m3.material.io/components/date-pickers/overview
//https://developer.android.com/develop/ui/compose/components/snackbar po dodaniu nowego treningu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {

    var datePickerState = rememberDatePickerState()

    val isDateSelected = remember {
        mutableStateOf(false)
    }

    val selectedDate = datePickerState.selectedDateMillis?.let { Date(it) }
    val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)

    Log.d("wybrana data", selectedDate.toString())

    if(selectedDate != null && !isDateSelected.value) {
        isDateSelected.value = true
        navController.navigate(AlternativeRoutes.HistoryDateDetails.destination + "/${selectedDate.let { myDateFormat.format(it) }}" )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {
        DatePicker(
            colors = DatePickerDefaults.colors(
                containerColor = InsideLevel1
            ),
            state = datePickerState
        )
    }
}


//var state = remember { mutableIntStateOf(1) }
//var calendar = remember { mutableStateOf(Calendar.getInstance()) }
//
//Log.d("halo", "xd ${state.value} ${calendar.value.get(Calendar.MONTH)}")
//
//val monthNames = arrayOf(
//    "Styczeń", "Luty", "Marzec", "Kwiecień", "Maj", "Czerwiec",
//    "Lipiec", "Sierpień", "Wrzesień", "Październik", "Listopad", "Grudzień"
//)

//Column(
//modifier = Modifier
//.fillMaxSize()
//.padding(horizontal = 25.dp, vertical = 15.dp)
//) {
//    Row(modifier = Modifier.fillMaxWidth()) {
//
//        Text(
//            modifier = Modifier.weight(1f),
//            text = monthNames[calendar.value.get(Calendar.MONTH)]
//        )
//
//        Button(
//            onClick = {
//                state.value -= 1
//                calendar.value.add(Calendar.MONTH, -1)
//                calendar.value = Calendar.getInstance().apply {
//                    add(Calendar.MONTH, -1)
//                }
//            },
//            modifier = Modifier.padding(horizontal = 2.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
//        ) {
//            Icon(
//                painter = rememberVectorPainter(image = Icons.Filled.KeyboardArrowLeft),
//                contentDescription = "before",
//                tint = Smola
//            )
//        }
//
//        Button(
//            onClick = {
//                state.value += 1
//                calendar.value.add(Calendar.MONTH, 1)
//            },
//            modifier = Modifier.padding(horizontal = 2.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
//        ) {
//            Icon(
//                painter = rememberVectorPainter(image = Icons.Filled.KeyboardArrowRight),
//                contentDescription = "before",
//                tint = Smola
//            )
//        }
//    }
//
//    Row(modifier = Modifier.fillMaxWidth()) {
//
//        LazyVerticalGrid(columns = GridCells.Fixed(7)) {
//            items((1..calendar.value.getActualMaximum(Calendar.DAY_OF_MONTH)).toList()) { day ->//liczba dni w miesiacu
//
//                calendar.value.set(Calendar.DAY_OF_MONTH, day)
//                val dayOfMonth = calendar.value.get(Calendar.DAY_OF_MONTH)
//
//                Row() {
//                    Box(
//                        modifier = Modifier
//                            .weight(1f)
//                            .background(Color.Transparent)
//                            .border(1.dp, Color.Black),
//                    ) {
//                        Text("$dayOfMonth")
//                    }
//                }
//            }
//
//
//        }
//
//    }
//}