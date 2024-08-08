package pl.matiu.kalistenika.training.timeExercise

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.internalStorage.ExerciseInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.ui.theme.Smola

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartTimeSeries(
    context: Context,
    exercise: TimeExercise,
    startStop: Boolean,
    onStarStopChange: (Boolean) -> Unit,
    navController: NavController
) {

    val seriesSong: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.dzwonek)
    val breakSong: MediaPlayer = MediaPlayer.create(LocalContext.current, R.raw.breaksong)

    var sekunder by rememberSaveable {
        mutableStateOf( 0)
    }

//    var startStop by rememberSaveable {
//        mutableStateOf(false)
//    }

    Column(modifier = Modifier.fillMaxWidth()
        .combinedClickable (
            onClick = {
                navController.navigate(Training.route + "/${exercise.trainingId}" + "/editTimeExercise" + "/${exercise.exerciseId}")
            },
            onDoubleClick = {
                ExerciseInternalStorageService().deleteTimeExerciseById(context, exercise.exerciseId)

                navController.navigate(Training.route + "/${exercise.trainingId}")
            }
        )
    ) {
        Text(
            text = exercise.exerciseName,
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Ilość serii: ${exercise.numberOfSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Czas dla serii: ${exercise.timeForSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Przerwa między seriami: ${exercise.breakBetweenSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Aktualny czas: $sekunder",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = "Aktualnie jest ${isSeriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)} przez:" +
                    "${seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries)} sekund",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

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

        if(startStop) {
            LaunchedEffect(Unit) {
                while(!isFullTimeForExercise(exercise.numberOfSeries, exercise.breakBetweenSeries, exercise.timeForSeries, sekunder) && startStop) {
                    delay(1000)
                    sekunder++

                    isEndOfSeriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries,
                        seriesSong, breakSong)

//                    if(exercise.stopTimer &&
//                        seriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries) == exercise.timeForSeries &&
//                        isSeriesOrBreak(sekunder, exercise.breakBetweenSeries, exercise.timeForSeries).equals("seria")) {
//                        startStop = false
//                    }

                }
            }
        }
    }
}

fun isEndOfSeriesOrBreak(
    actualTime: Int,
    breakTime: Int,
    seriesTime: Int,
    seriesSong: MediaPlayer,
    breakSong: MediaPlayer
) {
    //true - series, false - break

    //jesli czas jest rowny czasowi serii i aktualnie jest seria
    if(seriesOrBreak(actualTime, breakTime, seriesTime) == seriesTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("seria")) {
        seriesSong.start()
    }

    //jesli czas jest rowny czasowi przerwy i aktualnie jest przerwa
    if(seriesOrBreak(actualTime, breakTime, seriesTime) == breakTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("przerwa")) {
        breakSong.start()
    }
}

fun isSeriesOrBreak(actualTime: Int, breakTime: Int, seriesTime: Int): String {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if(modTime < seriesTime) {
        "seria"
    } else {
        "przerwa"
    }
}

fun seriesOrBreak(actualTime: Int, breakTime: Int, seriesTime: Int): Int {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if(modTime < seriesTime) {
        seriesTime - modTime
    } else {
        breakAndSeriesTime - modTime
    }
}

fun isFullTimeForExercise(numberOfSeries: Int, breakTime: Int, seriesTime: Int, actualTime: Int): Boolean {
    return (numberOfSeries * seriesTime) + ((numberOfSeries-1) * (breakTime)) <= actualTime
}