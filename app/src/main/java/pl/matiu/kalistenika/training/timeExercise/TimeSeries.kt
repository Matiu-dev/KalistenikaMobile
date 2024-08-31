package pl.matiu.kalistenika.training.timeExercise

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.internalStorage.TimeExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartTimeSeries(
    context: Context,
    exercise: TimeExercise,
    startStop: Boolean,
    onStarStopChange: (Boolean) -> Unit,
    navController: NavController,
    endOfSeries: Boolean,
    onEndOfSeriesChange: (Boolean) -> Unit
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

    var currentProgress by remember { mutableStateOf(0f) }
    var loading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


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
                    TrainingInternalStorageService().getTrainingNameById(context, exercise.trainingId)
                )

                navController.navigate(Training.route + "/${exercise.trainingId}")
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

        Text(
            text = exercise.exerciseName,
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

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
            modifier = Modifier.fillMaxWidth()
                .padding(5.dp)
                .height(25.dp)
                .clip(RoundedCornerShape(50))
                .border(5.dp, color = Smola, shape = RoundedCornerShape(50))
                .background(InsideLevel1),
            color = InsideLevel2
        )

        Text(
            text = "Koniec ${isSeriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)} za " +
                    "${seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)} sekund",
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

        if (startStop) {
            LaunchedEffect(Unit) {
                while (!isFullTimeForExercise(
                        exercise.numberOfSeries,
                        exercise.breakBetweenSeries,
                        exercise.timeForSeries,
                        sekunder
                    )
                ) {
                    delay(1000)
                    sekunder++

                    currentProgress = (isSeriesOrBreakSecond(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries, exercise) -
                            seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)).toFloat() /
                            isSeriesOrBreakSecond(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries, exercise)

                    isEndOfSeriesOrBreak(
                        sekunder, exercise.breakBetweenSeries, exercise.timeForSeries,
                        seriesSong, breakSong
                    )

//                    if(exercise.stopTimer &&
//                        seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries) == exercise.timeForSeries &&
//                        isSeriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries).equals("seria")) {
//                        startStop = false
//                    }

                }

                onStarStopChange(!startStop)
                onEndOfSeriesChange(!endOfSeries)
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

fun isSeriesOrBreakSecond(actualTime: Int, breakTime: Int, seriesTime: Int, exercise: TimeExercise): Int {
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