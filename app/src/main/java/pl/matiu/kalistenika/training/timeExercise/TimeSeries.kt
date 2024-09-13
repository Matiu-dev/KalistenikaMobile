package pl.matiu.kalistenika.training.timeExercise

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TooltipBoxScope
import androidx.compose.material3.TooltipDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.internalStorage.TimeExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun StartTimeSeries(
    context: Context,
    exercise: TimeExercise,
    startStop: Boolean,
    onStarStopChange: (Boolean) -> Unit,
    navController: NavController,
    endOfSeries: Boolean,
    onEndOfSeriesChange: (Boolean) -> Unit,
    pagerState: PagerState,
) {

    val seriesSong = remember { MediaPlayer.create(context, R.raw.dzwonek) }
    val breakSong = remember { MediaPlayer.create(context, R.raw.breaksong) }

    var sekunder by rememberSaveable { mutableStateOf(0) }

    DisposableEffect(Unit) {
        onDispose {
            seriesSong.release()
            breakSong.release()
        }
    }

    var currentProgress by rememberSaveable { mutableStateOf(0f) }
    var initialBreakTime by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if(showDialog) {
        DialogWithImage(
            onDismissRequest = { showDialog = false },
            onConfirmation = {  },
            painter = painterResource(id = android.R.drawable.ic_dialog_info),
            imageDescription = "opis"
        )
    }

    Column(modifier = Modifier
        .fillMaxWidth()
        .combinedClickable(
            onClick = {
                navController.navigate(Training.route + "/${exercise.trainingId}" + "/editTimeExercise" + "/${exercise.exerciseId}")
            },
            onDoubleClick = {
                TimeExerciseInternalStorage().deleteTimeExerciseById(
                    context,
                    exercise.exerciseId,
                    exercise.trainingId,
                    TrainingInternalStorageService().getTrainingNameById(
                        context,
                        exercise.trainingId
                    )
                )

                navController.navigate(Training.route + "/${exercise.trainingId}")
            }
        )
    ) {

        //do testu
//        Text(
//            text = exercise.toString(),
//            color = Smola,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )
//
//        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        //do testu

        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically) {

            Spacer(modifier = Modifier.weight(1f))

            Box(modifier = Modifier.fillMaxWidth().weight(1f),
                contentAlignment = Alignment.Center) {
                Text(
                    text = exercise.exerciseName,
                    color = Smola,
                )
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = { showDialog = true },
                modifier = Modifier.weight(1f))
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
            text = "Ilość serii: ${exercise.numberOfSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Czas dla serii: ${exercise.timeForSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(thickness = 2.dp, color = Smola, modifier = Modifier.padding(5.dp))

        Text(
            text = "Przerwa między seriami: ${exercise.breakBetweenSeries}",
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
                    exercise.breakBetweenSeries,
                    exercise.timeForSeries
                )
            } za " +
                    "${
                        seriesOrBreak(
                            sekunder,
                            exercise.breakBetweenSeries,
                            exercise.timeForSeries
                        )
                    } sekund",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

//        if (startStop && !loading) {
//            loading = true
//            currentProgress = 0f
//            scope.launch {
//                loadProgress(
//                    { progress ->
//                        println("Current progress: $progress")
//                        currentProgress = progress
//                    },
//                    seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)
//                )
//                loading = false
//            }
//        }


//        Text(
//            text = "Wykonanych serii: ${floor((sekunder.toDouble()/exercise.breakBetweenSeries.toDouble()) + 1)}",
//            color = ZielonyNapis,
//            modifier = Modifier.align(Alignment.CenterHorizontally)
//        )


//        Button(
//            onClick = {
//                startStop = !startStop
//            },
//            modifier = Modifier.fillMaxWidth(),
//            colors = ButtonDefaults.buttonColors(containerColor = Smola)
//        ) {
//            Text(text = if (startStop) "Stop" else "Start", color = Color.White)
//        }

//        if (startStop) {
        LaunchedEffect(startStop) {

            if (startStop) {

                //czekanie jeszcze jednej przerwy i przejscie do kolejnego cwiczenia
                if (initialBreakTime != exercise.breakBetweenSeries) {
                    if (pagerState.currentPage == pagerState.pageCount) {
                        onStarStopChange(!startStop)
                    } else {
                        for (i in initialBreakTime..exercise.breakBetweenSeries) {
                            currentProgress = i.toFloat() / exercise.breakBetweenSeries.toFloat()

                            initialBreakTime = i
                            delay(1000)
                        }

                        seriesSong.start()
                    }
                }


                while (!isFullTimeForExercise(
                        exercise.numberOfSeries,
                        exercise.breakBetweenSeries,
                        exercise.timeForSeries,
                        sekunder
                    )
                ) {
                    delay(1000)
                    sekunder++

                    currentProgress = (isSeriesOrBreakSecond(
                        sekunder,
                        exercise.breakBetweenSeries,
                        exercise.timeForSeries,
                        exercise
                    ) -
                            seriesOrBreak(
                                sekunder,
                                exercise.breakBetweenSeries,
                                exercise.timeForSeries
                            )).toFloat() /
                            isSeriesOrBreakSecond(
                                sekunder,
                                exercise.breakBetweenSeries,
                                exercise.timeForSeries,
                                exercise
                            )

                    isEndOfSeriesOrBreak(
                        sekunder, exercise.breakBetweenSeries, exercise.timeForSeries,
                        seriesSong, breakSong
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
) {
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
                Image(
                    painter = painter,
                    contentDescription = imageDescription,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .height(160.dp)
                )
                Text(
                    text = "This is a dialog with buttons and an image.",
                    modifier = Modifier.padding(16.dp),
                )
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
        seriesSong.start()
    }

    //jesli czas jest rowny czasowi przerwy i aktualnie jest przerwa
    if (seriesOrBreak(actualTime, breakTime, seriesTime) == breakTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("przerwy")
    ) {
        breakSong.start()
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
        exercise.timeForSeries
    } else {
        exercise.breakBetweenSeries
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