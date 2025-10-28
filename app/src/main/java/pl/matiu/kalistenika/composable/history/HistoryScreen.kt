package pl.matiu.kalistenika.composable.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import java.util.Calendar
import java.util.Locale

//TODO https://m3.material.io/components/date-pickers/overview
//https://developer.android.com/develop/ui/compose/components/snackbar po dodaniu nowego treningu
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryScreen(navController: NavController) {

    val localePL = Locale("pl", "PL")
    val calendar = Calendar.getInstance(localePL)
    calendar.set(Calendar.DAY_OF_MONTH, 1)

    val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
    val weeksInMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)
    val actualMonth = calendar.get(Calendar.MONTH)

    val daysPerWeek = MutableList(weeksInMonth) { 0 }

    for (day in 1..daysInMonth) {
        calendar.set(Calendar.DAY_OF_MONTH, day)
        val weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH) - 1
        daysPerWeek[weekOfMonth]++
    }

    calendar.set(Calendar.DAY_OF_MONTH, 1)

//    val daysQueue = listOf(2, 3, 4, 5, 6, 7, 1)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {
        Column {
            Row(horizontalArrangement = Arrangement.Center) {
                Text(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp),
                    textAlign = TextAlign.Center,
                    text = "${CalendarHelper.monthQueue[actualMonth]}",
                    color = Color.Black,
                    fontSize = 30.sp
                )
            }

            Row(horizontalArrangement = Arrangement.Center) {
                Text(modifier = Modifier.weight(1f), text = "Mon", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Tue", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Wed", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Thu", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Fri", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Sat", textAlign = TextAlign.Center, color = Color.Black,)
                Text(modifier = Modifier.weight(1f), text = "Sun", textAlign = TextAlign.Center, color = Color.Black,)
            }

            Column(modifier = Modifier.fillMaxWidth()) {
                for(j in 1..weeksInMonth) {
                    calendar.set(Calendar.WEEK_OF_MONTH, j)

                    Row(modifier = Modifier.weight(1f)) {


                        for(i in CalendarHelper.daysQueue) {
                            calendar.set(Calendar.DAY_OF_WEEK, i)


                            Box(modifier = Modifier
                                .weight(1F)
                                .fillMaxSize(),
                                contentAlignment = Alignment.Center

                            ) {
                                TextButton(
                                    modifier = Modifier.fillMaxWidth(),
                                    onClick = {
                                        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH))
                                        calendar.set(Calendar.WEEK_OF_MONTH, j)
                                        calendar.set(Calendar.DAY_OF_WEEK, i)
                                        Log.d("calendarDate", "${calendar.time}")
                                        Log.d("calendarDate", "${calendar.get(Calendar.MONTH)}")
                                        navController.navigate(AlternativeRoutes.HistoryDateDetails.destination + "/${calendar.time.let { CalendarHelper.myDateFormat.format(it) }}" )
                                    }
                                ) {
                                    if(actualMonth == calendar.get(Calendar.MONTH)) {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "${calendar.get(Calendar.DAY_OF_MONTH)}",
                                            textAlign = TextAlign.Center,
                                            color = Color.Black,
                                        )
                                    } else {
                                        Text(
                                            modifier = Modifier.fillMaxWidth(),
                                            text = "${calendar.get(Calendar.DAY_OF_MONTH)}",
                                            textAlign = TextAlign.Center,
                                            color = Color.Gray
                                        )
                                    }

                                }
                            }
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

//old working

//    // using build in elements
////    var datePickerState = rememberDatePickerState()
////
////    val isDateSelected = remember {
////        mutableStateOf(false)
////    }
////
////    val selectedDate = datePickerState.selectedDateMillis?.let { Date(it) }
////    val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
////
////    Log.d("wybrana data", selectedDate.toString())
////
////    if(selectedDate != null && !isDateSelected.value) {
////        isDateSelected.value = true
////        navController.navigate(AlternativeRoutes.HistoryDateDetails.destination + "/${selectedDate.let { myDateFormat.format(it) }}" )
////    }
////
////    Surface(
////        modifier = Modifier.fillMaxSize(),
////        color = InsideLevel1,
////    ) {
////        DatePicker(
////            colors = DatePickerDefaults.colors(
////                containerColor = InsideLevel1
////            ),
////            state = datePickerState
////        )
////    }