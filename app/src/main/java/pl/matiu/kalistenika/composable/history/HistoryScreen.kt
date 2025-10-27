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
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
import kotlin.time.Duration.Companion.days

//TODO https://m3.material.io/components/date-pickers/overview
//https://developer.android.com/develop/ui/compose/components/snackbar po dodaniu nowego treningu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {

    // using build in elements
//    var datePickerState = rememberDatePickerState()
//
//    val isDateSelected = remember {
//        mutableStateOf(false)
//    }
//
//    val selectedDate = datePickerState.selectedDateMillis?.let { Date(it) }
//    val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
//
//    Log.d("wybrana data", selectedDate.toString())
//
//    if(selectedDate != null && !isDateSelected.value) {
//        isDateSelected.value = true
//        navController.navigate(AlternativeRoutes.HistoryDateDetails.destination + "/${selectedDate.let { myDateFormat.format(it) }}" )
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = InsideLevel1,
//    ) {
//        DatePicker(
//            colors = DatePickerDefaults.colors(
//                containerColor = InsideLevel1
//            ),
//            state = datePickerState
//        )
//    }

    //custom

    val localePL = Locale("pl", "PL")
    val calendar = Calendar.getInstance(localePL)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val weeksInMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)

    val daysPerWeek = MutableList(weeksInMonth) { 0 }

    for (day in 1..daysInMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) - 1
        daysPerWeek[weekOfMonth]++
    }

    calendar.set(Calendar.DAY_OF_MONTH, 1)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            for(j in 0..daysPerWeek.size-1) {//number of weeks

                Row(modifier = Modifier.weight(1f)) {

                    val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 2

                    //uzupelnianie pustych miejsc od poczatku
                    for(k in 0..firstDayOfWeek-1) {
                        Box(modifier = Modifier.weight(1F))
                    }

                    //srodek
                    for(i in 0..daysPerWeek.get(j)-1) {
                        Box(modifier = Modifier
                            .weight(1F)
                            .fillMaxSize(),
                            contentAlignment = Alignment.Center

                        ) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    val start = Calendar.getInstance().apply {
                                        set(Calendar.YEAR, 2025)
                                        set(Calendar.MONTH, Calendar.OCTOBER)
                                        set(Calendar.WEEK_OF_MONTH, 1)
                                        set(Calendar.DAY_OF_WEEK, 2)//29 poniedzialek
                                    }
                                    Log.d("test", "kliknieto ${start.time}")
                                }
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    text = "${calendar.get(Calendar.DAY_OF_MONTH)}",
                                    textAlign = TextAlign.Center
                                )
                            }


                            calendar.set(
                                Calendar.DAY_OF_MONTH,
                                calendar.get(Calendar.DAY_OF_MONTH) + 1
                            )
                        }
                    }

                    //puste miejsca na koncu
                    if(firstDayOfWeek == 0) {
                        for(k in 0..7-daysPerWeek.get(j) - 1) {
                            Box(modifier = Modifier.weight(1F))
                        }
                    }
                }

            }

        }
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