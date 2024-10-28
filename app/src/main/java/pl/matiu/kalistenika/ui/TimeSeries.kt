package pl.matiu.kalistenika.ui

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.media.StartSong
import pl.matiu.kalistenika.viewModel.SeriesViewModel
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StartTimeSeries(
    context: Context,
    exercise: TimeExercise,
    trainingName: String,
    startStop: Boolean,
    onStarStopChange: (Boolean) -> Unit,
    navController: NavController,
    endOfSeries: Boolean,
    onEndOfSeriesChange: (Boolean) -> Unit,
    pagerState: PagerState,
) {
    var sekunder by rememberSaveable { mutableStateOf(0) }

    val seriesViewModel: SeriesViewModel = viewModel()

//    DisposableEffect(Unit) {
//        onDispose {
//            seriesSong.release()
//            StartSong.seriesSong.release()
//            breakSong.release()
//        }
//    }

    var currentProgress by rememberSaveable { mutableStateOf(0f) }
    var initialBreakTime by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DialogWithImage(
            onDismissRequest = { showDialog = false },
            onConfirmation = { },
            painter = painterResource(id = android.R.drawable.ic_dialog_info),
            imageDescription = "opis",
            exerciseName = exercise.timeExerciseName
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(
            onClick = {
                navController.navigate(AlternativeRoutes.EditTimeSeries.destination + "/${exercise.trainingId}" + "/${exercise.timeExerciseId}")
            },
            onDoubleClick = {
                seriesViewModel.deleteTimeSeries(timeExercise = exercise)
                navController.navigate(MainRoutes.Training.destination + "/${trainingName}" + "/${exercise.trainingId}")
            }
        )
    ) {

        //do testu
        Text(
            text = exercise.toString(),
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        //do testu

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    RealTimeDatabaseService().writeDataHistory(exercise)
                    Toast.makeText(context, "dodano ćwiczenie do historii", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.weight(1f)
            )
            {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.AddCircle),
                    contentDescription = "before",
                    tint = Smola,
                    modifier = Modifier.background(Color.Transparent)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = exercise.timeExerciseName,
                    color = Smola,
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { showDialog = true },
                modifier = Modifier.weight(1f)
            )
            {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Info),
                    contentDescription = "before",
                    tint = Smola,
                    modifier = Modifier.background(Color.Transparent)
                )
            }
        }

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Ilość serii: ${exercise.numberOfTimeSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Czas dla serii: ${exercise.timeForTimeSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Przerwa między seriami: ${exercise.breakBetweenTimeSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Aktualny czas: $sekunder",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        LinearProgressIndicator(
            progress = currentProgress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .height(25.dp)
                .clip(RoundedCornerShape(50))
                .border(5.dp, color = Smola, shape = RoundedCornerShape(50))
                .background(InsideLevel1),
            color = InsideLevel2
        )

        Text(
            text = "Koniec ${
                isSeriesOrBreak(
                    sekunder,
                    exercise.breakBetweenTimeSeries,
                    exercise.timeForTimeSeries
                )
            } za " +
                    "${
                        seriesOrBreak(
                            sekunder,
                            exercise.breakBetweenTimeSeries,
                            exercise.timeForTimeSeries
                        )
                    } sekund",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        LaunchedEffect(startStop) {

            if (startStop) {

                //czekanie jeszcze jednej przerwy i przejscie do kolejnego cwiczenia
                if (initialBreakTime != exercise.breakBetweenTimeSeries) {
                    if (pagerState.currentPage == pagerState.pageCount) {
                        onStarStopChange(!startStop)
                    } else {
                        for (i in initialBreakTime..exercise.breakBetweenTimeSeries) {
                            currentProgress =
                                i.toFloat() / exercise.breakBetweenTimeSeries.toFloat()

                            initialBreakTime = i
                            delay(1000)
                        }

                        StartSong.seriesSong.start()
                    }
                }


                while (!isFullTimeForExercise(
                        exercise.numberOfTimeSeries,
                        exercise.breakBetweenTimeSeries,
                        exercise.timeForTimeSeries,
                        sekunder
                    )
                ) {
                    delay(1000)
                    sekunder++

                    currentProgress = (isSeriesOrBreakSecond(
                        sekunder,
                        exercise.breakBetweenTimeSeries,
                        exercise.timeForTimeSeries,
                        exercise
                    ) -
                            seriesOrBreak(
                                sekunder,
                                exercise.breakBetweenTimeSeries,
                                exercise.timeForTimeSeries
                            )).toFloat() /
                            isSeriesOrBreakSecond(
                                sekunder,
                                exercise.breakBetweenTimeSeries,
                                exercise.timeForTimeSeries,
                                exercise
                            )

                    isEndOfSeriesOrBreak(
                        sekunder, exercise.breakBetweenTimeSeries, exercise.timeForTimeSeries,
                        StartSong.seriesSong, StartSong.breakSong
                    )

                }

                onEndOfSeriesChange(!endOfSeries)
            }
        }
    }
}

@Composable
fun DialogWithImage(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    painter: Painter,
    imageDescription: String,
    exerciseName: String
) {

    var exercise = remember {
        mutableStateOf<List<ExerciseApi>>(emptyList())
    }

    RealTimeDatabaseService().loadData { exercises ->
        exercise.value = exercises.filter { it.name == exerciseName }
    }

    Dialog(onDismissRequest = { onDismissRequest() }) {
        // Draw a rectangle shape with rounded corners inside the dialog
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(375.dp)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Image(
//                    painter = painter,
//                    contentDescription = imageDescription,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier
//                        .height(160.dp)
//                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Dismiss")
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Confirm")
                    }
                }

                //dane
                exercise.value.let { exercise ->
                    if (exercise.isNotEmpty()) {
                        LazyColumn() {
                            items(exercise) {
                                Text(
                                    text = it.name,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.type,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.muscle,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.equipment,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.difficulty,
                                    modifier = Modifier.padding(16.dp),
                                )
                                Text(
                                    text = it.instructions,
                                    modifier = Modifier.padding(16.dp),
                                )
                            }
                        }
                    }
                }

                //dane
            }
        }
    }
}
//suspend fun loadProgress(updateProgress: (Float) -> Unit, time: Int) {
//    for (i in 0..time) {
//        updateProgress(i.toFloat() / time)
//        delay(1000)
//    }
//}

fun isEndOfSeriesOrBreak(
    actualTime: Int,
    breakTime: Int,
    seriesTime: Int,
    seriesSong: MediaPlayer,
    breakSong: MediaPlayer
) {
    //true - series, false - break

    //jesli czas jest rowny czasowi serii i aktualnie jest seria
    if (seriesOrBreak(actualTime, breakTime, seriesTime) == seriesTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("serii")
    ) {
        StartSong.seriesSong.start()
    }

    //jesli czas jest rowny czasowi przerwy i aktualnie jest przerwa
    if (seriesOrBreak(actualTime, breakTime, seriesTime) == breakTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("przerwy")
    ) {
        StartSong.breakSong.start()
    }
}

fun isSeriesOrBreak(actualTime: Int, breakTime: Int, seriesTime: Int): String {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if (modTime < seriesTime) {
        "serii"
    } else {
        "przerwy"
    }
}

fun isSeriesOrBreakSecond(
    actualTime: Int,
    breakTime: Int,
    seriesTime: Int,
    exercise: TimeExercise
): Int {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if (modTime < seriesTime) {
        exercise.timeForTimeSeries
    } else {
        exercise.breakBetweenTimeSeries
    }
}

fun seriesOrBreak(actualTime: Int, breakTime: Int, seriesTime: Int): Int {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if (modTime < seriesTime) {
        seriesTime - modTime
    } else {
        breakAndSeriesTime - modTime
    }
}

fun isFullTimeForExercise(
    numberOfSeries: Int,
    breakTime: Int,
    seriesTime: Int,
    actualTime: Int
): Boolean {
    return (numberOfSeries * seriesTime) + ((numberOfSeries - 1) * (breakTime)) <= actualTime
}