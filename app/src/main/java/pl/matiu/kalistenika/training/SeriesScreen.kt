package pl.matiu.kalistenika.training

import StartRepetitionSeries
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.internalStorage.RepetitionExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TimeExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.RepetitionExercise
import pl.matiu.kalistenika.training.model.SeriesInterface
import pl.matiu.kalistenika.training.timeExercise.StartTimeSeries
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

//progression indicator do linii miedzy kulkami
//bottom albo side sheets - do menu dolnego lub boczbnego
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SeriesScreen(
    navController: NavController,
    trainingId: Int?
) {
    var addTraining by rememberSaveable { mutableStateOf(false) }
    var actualTraining by rememberSaveable { mutableIntStateOf(0) }
    var startStop by rememberSaveable { mutableStateOf(false) }

    val timeExerciseInternalStorage: TimeExerciseInternalStorage = TimeExerciseInternalStorage()
    val repetitionExerciseInternalStorage: RepetitionExerciseInternalStorage = RepetitionExerciseInternalStorage()

    var timeExerciseList: List<SeriesInterface> =
        timeExerciseInternalStorage.loadTimeExerciseFromInternalStorage(LocalContext.current,
            TrainingInternalStorageService().getTrainingNameById(LocalContext.current, trainingId))
            .filter { v -> v.trainingId == trainingId }

    var repetitionExerciseList: List<SeriesInterface> =
        repetitionExerciseInternalStorage.loadRepetitionExerciseFromInternalStorage(LocalContext.current,
            TrainingInternalStorageService().getTrainingNameById(LocalContext.current, trainingId))
            .filter { v -> v.trainingId == trainingId }

    var exerciseList: List<SeriesInterface> = (timeExerciseList + repetitionExerciseList ).sortedBy { it.positionInTraining }
//    var showBottomSheet by remember { mutableStateOf(true) }

    val pagerState = rememberPagerState(
        pageCount = {
            exerciseList.size
        }
    )

    var endOfSeries by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            if (!addTraining) {

                //buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .border(1.dp, Smola, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp),
                    horizontalArrangement = Arrangement.Center,
                ) {

                    Row(modifier = Modifier.padding(vertical = 5.dp)) {
                        Button(
                            onClick = {
                                if (actualTraining != 0) {
                                    actualTraining--
                                }

                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                }
                            },
                            modifier = Modifier.padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                        ) {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.KeyboardArrowLeft),
                                contentDescription = "before",
                                tint = Smola
                            )

                        }

                        Button(
                            onClick = { startStop = !startStop },
                            modifier = Modifier.padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                        ) {
                            if (!startStop) {
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Filled.PlayArrow),
                                    contentDescription = "play",
                                    tint = Smola
                                )
                            } else {
                                Icon(
                                    painter = rememberVectorPainter(image = Icons.Filled.Menu),
                                    contentDescription = "play",
                                    tint = Smola
                                )
                            }

                        }

                        Button(
                            onClick = {
                                if (actualTraining != exerciseList.size - 1) {
                                    actualTraining++
                                }

                                coroutineScope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            },
                            modifier = Modifier.padding(horizontal = 2.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                        ) {
                            Icon(
                                painter = rememberVectorPainter(image = Icons.Filled.KeyboardArrowRight),
                                contentDescription = "next",
                                tint = Smola
                            )
                        }
                    }
                }

                Row {
                    Divider(
                        color = Smola,
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
//                        .border(1.dp, Smola, RoundedCornerShape(8.dp))
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {

                    VerticalPager(
                        state = pagerState
                    ) { index ->

                        //gora
                        Column(
//                            modifier = Modifier.weight(2f),
                            horizontalAlignment = Alignment.CenterHorizontally,

                            ) {

                            if (index == actualTraining) {
                                when (exerciseList[index]) {

                                    is RepetitionExercise -> StartRepetitionSeries(
                                        exercise = exerciseList[index] as RepetitionExercise,
                                        startStop = startStop,
                                        onStarStopChange = { startStop = it },
                                        navController = navController,
                                        context = LocalContext.current,

                                        endOfSeries = endOfSeries
                                    ) { endOfSeries = it }

                                    is TimeExercise -> StartTimeSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as TimeExercise,
                                        startStop = startStop,
                                        onStarStopChange = { startStop = it },
                                        navController = navController,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it }
                                    )
                                }
                            } else {
                                when (exerciseList[index]) {
                                    //TODO zamienic te obiekty na Cardy https://developer.android.com/develop/ui/compose/components/card
                                    is RepetitionExercise -> StartRepetitionSeries(
                                        exercise = exerciseList[index] as RepetitionExercise,
                                        startStop = false,
                                        onStarStopChange = { false },
                                        navController = navController,
                                        context = LocalContext.current,
                                        endOfSeries = endOfSeries
                                    ) { endOfSeries = it }

                                    is TimeExercise -> StartTimeSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as TimeExercise,
                                        false,
                                        { false },
                                        navController = navController,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it }
                                    )
                                }
                            }

                            StepProgressBar(
                                numberOfSteps = exerciseList.indices,
                                actualTraining = actualTraining
                            )
                        }

                    }

                }


            } else {
                navController.navigate(Training.route + "/${trainingId}" + "/createSeries")
            }
        }
    }
}

@Composable
fun StepProgressBar(numberOfSteps: IntRange, actualTraining: Int) {

    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        for (i in numberOfSteps) {

            Box(modifier = Modifier.weight(1f)) {

                Divider(
                    modifier = Modifier.align(Alignment.CenterStart),
                    color = Smola,
                    thickness = 2.dp
                )

                Canvas(
                    modifier = Modifier
                        .size(15.dp)
                        .align(Alignment.CenterEnd)
                        .border(
                            shape = CircleShape,
                            width = 2.dp,
                            color = Smola
                        ),
                    onDraw = {
                        drawCircle(
                            color = if (i < actualTraining) Smola
                            else if (i > actualTraining) InsideLevel1
                            else Color.Gray
                        )
                    })
            }
        }
    }

}

@Composable
fun AddExerciseButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = InsideLevel2,
        contentColor = Smola
    ) {
        Icon(Icons.Filled.Add, "add exercise")
    }
}


