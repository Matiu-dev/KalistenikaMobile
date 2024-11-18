import android.R
import android.content.Context
import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import pl.matiu.kalistenika.media.StartSong
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.viewModel.SeriesViewModel
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.composable.series.DialogWithImage
import pl.matiu.kalistenika.composable.series.getFullTimeForExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StartRepetitionSeries(
    context: Context,
    exercise: RepetitionExercise,
    trainingName: String,
    startStop: Boolean,
    onStarStopChange: (Boolean) -> Unit,
    navController: NavController,
    pagerState: PagerState,
    endOfSeries: Boolean,
    onEndOfSeriesChange: (Boolean) -> Unit,
) {

//    val seriesSong = remember { MediaPlayer.create(context, R.raw.dzwonek) }
//    val breakSong = remember { MediaPlayer.create(context, R.raw.breaksong) }

    val seriesViewModel: SeriesViewModel = viewModel()

    var sekunder by rememberSaveable {
        mutableIntStateOf(0)
    }

    DisposableEffect(Unit) {
        onDispose {
//            seriesSong.release()
//            breakSong.release()
        }
    }

    var currentProgress by remember { mutableStateOf(0f) }
    var initialBreakTime by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        DialogWithImage(
            onDismissRequest = { showDialog = false },
            onConfirmation = { },
            painter = painterResource(id = R.drawable.ic_dialog_info),
            imageDescription = "opis",
            exerciseName = exercise.repetitionExerciseName
        )
    }

    var isClicked by remember { mutableStateOf(false) }
    if(isClicked) {
        val isLodaing = seriesViewModel.isLoading.collectAsState()

        if(!isLodaing.value) {
            seriesViewModel.deleteRepetitionSeries(repetitionExercise = exercise)
            isClicked = false
            navController.navigate(MainRoutes.Training.destination + "/${trainingName}" + "/${exercise.trainingId}")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = {
                    navController.navigate(AlternativeRoutes.EditRepetitionSeries.destination + "/${exercise.trainingId}" + "/${exercise.repetitionExerciseId}")
                },
                onDoubleClick = {
//                    seriesViewModel.deleteRepetitionSeries(repetitionExercise = exercise)
//                    navController.navigate(MainRoutes.Training.destination + "/${trainingName}" + "/${exercise.trainingId}")
                    isClicked = true
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
                    text = exercise.repetitionExerciseName,
                    color = Smola,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
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

        Divider(
            thickness = 2.dp, color = Smola, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )

        Text(
            text = "Ilość serii: ${exercise.numberOfRepetitionSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(
            thickness = 2.dp, color = Smola, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )

        Text(
            text = "Liczba powtórzeń w serii: ${exercise.numberOfReps}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(
            thickness = 2.dp, color = Smola, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )

        Text(
            text = "Zatrzymanie czasu po serii: ${if (exercise.stopTimer) "tak" else "nie"}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(
            thickness = 2.dp, color = Smola, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )

        Text(
            text = "Przerwa między seriami: ${exercise.breakBetweenRepetitionSeries}",
            color = Smola,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Divider(
            thickness = 2.dp, color = Smola, modifier = Modifier
                .padding(horizontal = 5.dp, vertical = 10.dp)
        )

        MyProgressIndicator(currentProgress = currentProgress,
            sekunder = sekunder,
            exercise = exercise)

        LaunchedEffect(startStop) {

            if (startStop) {

                if (initialBreakTime != exercise.breakBetweenRepetitionSeries) {
                    if (pagerState.currentPage == pagerState.pageCount) {
                        onStarStopChange(!startStop)
                    } else {
                        //czekanie jeszcze jednej przerwy i przejscie do kolejnego cwiczenia
                        for (i in initialBreakTime..exercise.breakBetweenRepetitionSeries) {
                            currentProgress =
                                i.toFloat() / exercise.breakBetweenRepetitionSeries.toFloat()

                            initialBreakTime = i
                            delay(1000)
                        }

                        StartSong.seriesSong.start()
                        if(exercise.stopTimer) {
                            onStarStopChange(!startStop)
                        }
                    }
                }



                while (sekunder < exercise.breakBetweenRepetitionSeries * (exercise.numberOfRepetitionSeries-1) && startStop) {
                    delay(1000)
                    sekunder++

                    currentProgress = (sekunder % exercise.breakBetweenRepetitionSeries).toFloat() /
                            exercise.breakBetweenRepetitionSeries

                    if (exercise.stopTimer && sekunder % exercise.breakBetweenRepetitionSeries == 0) {
                        onStarStopChange(!startStop)
//                        startStop = !startStop
                    }

                    if (sekunder % exercise.breakBetweenRepetitionSeries == 0) {
                        StartSong.breakSong.start()
                    }
                }

                onStarStopChange(!startStop)
                onEndOfSeriesChange(!endOfSeries)
            }
        }
    }
}

fun isEndOfSeriesOrBreak(
    actualTime: Int,
    breakTime: Int,
    seriesTime: Int,
) {
    //true - series, false - break

    //jesli czas jest rowny czasowi serii i aktualnie jest seria
    if (seriesOrBreak(actualTime, breakTime, seriesTime) == seriesTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("seria")
    ) {
        StartSong.seriesSong.start()
    }

    //jesli czas jest rowny czasowi przerwy i aktualnie jest przerwa
    if (seriesOrBreak(actualTime, breakTime, seriesTime) == breakTime &&
        isSeriesOrBreak(actualTime, breakTime, seriesTime).equals("przerwa")
    ) {
        StartSong.breakSong.start()
    }
}

fun isSeriesOrBreak(actualTime: Int, breakTime: Int, seriesTime: Int): String {
    val breakAndSeriesTime: Int = breakTime + seriesTime
    val modTime: Int = actualTime % breakAndSeriesTime

    return if (modTime < seriesTime) {
        "seria"
    } else {
        "przerwa"
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

@Composable
fun MyProgressIndicator(currentProgress: Float, sekunder: Int, exercise: RepetitionExercise) {

    Row(modifier = Modifier.fillMaxWidth()) {

        Box(Modifier.weight(1f)) {

            CircularProgressIndicator(
                progress = sekunder / (
                        (exercise.numberOfRepetitionSeries - 1) * exercise.breakBetweenRepetitionSeries
                ).toFloat(),
                modifier = Modifier
                    .padding(5.dp)
                    .background(InsideLevel1)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                color = InsideLevel2,
                trackColor = Color.Gray,
                strokeWidth = 25.dp
            )

            Text(
                text = "${sekunder} / ${
                    (exercise.numberOfRepetitionSeries - 1) * exercise.breakBetweenRepetitionSeries
                }",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Box(Modifier.weight(1f)) {

            CircularProgressIndicator(
                progress = currentProgress,

                modifier = Modifier
                    .padding(5.dp)
                    .background(InsideLevel1)
                    .fillMaxWidth()
                    .aspectRatio(1f),
                color = InsideLevel2,
                trackColor = Color.Gray,
                strokeWidth = 25.dp
            )

            Text(
                text = "${exercise.breakBetweenRepetitionSeries - sekunder % exercise.breakBetweenRepetitionSeries}",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .wrapContentHeight(align = Alignment.CenterVertically),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }

    Row(modifier = Modifier.fillMaxWidth()) {

        Text(
            text = "Czas dla całego ćwiczenia",
            color = Smola,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Czas do końca przerwy",
            color = Smola,
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            textAlign = TextAlign.Center
        )
    }
}