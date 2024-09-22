package pl.matiu.kalistenika.ui

import StartRepetitionSeries
import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.PagerState
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.myViewModel.SeriesViewModel
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.SeriesInterface
import pl.matiu.kalistenika.trainingModel.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

//progression indicator do linii miedzy kulkami
//bottom albo side sheets - do menu dolnego lub boczbnego
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun SeriesScreen(
    navController: NavController,
    trainingId: Int?
) {
    val context = LocalContext.current
    val viewModel: SeriesViewModel = viewModel()
    val isLoading by viewModel.isLoading.collectAsState()
    val exerciseList by viewModel.exerciseList.collectAsState()

    LaunchedEffect(trainingId) {
        viewModel.loadData(context, trainingId)
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        exerciseList?.let {
            trainingId?.let { it1 ->
                SeriesScreenView(
                    exerciseList = it,
                    navController = navController,
                    trainingId = it1
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun LoadingScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Loading...")
        }

    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SeriesScreenView(
    exerciseList: List<SeriesInterface>,
    navController: NavController,
    trainingId: Int
) {

    var addTraining by rememberSaveable { mutableStateOf(false) }
    var startStop by rememberSaveable { mutableStateOf(false) }
    var endOfSeries by rememberSaveable { mutableStateOf(false) }

    var pagerState = rememberPagerState(
        pageCount = {
            exerciseList.size
        }
    )

    val coroutineScope = rememberCoroutineScope()

    //jesli skonczy sie seria to ten fragment kodu powoduje przejscie do kolejnego cwiczenia
    if (endOfSeries) {

        coroutineScope.launch {
            pagerState.animateScrollToPage(pagerState.currentPage + 1)
        }

        endOfSeries = false
    }

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
                                Image(
                                    painter = painterResource(id = R.drawable.pauseimage3),
                                    contentDescription = "play",
                                    modifier = Modifier.size(24.dp).background(Color.Transparent)
                                    )
                            }

                        }

                        Button(
                            onClick = {
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

                            if (index == pagerState.currentPage) {
                                when (exerciseList[index]) {

                                    is RepetitionExercise -> StartRepetitionSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as RepetitionExercise,
                                        startStop = startStop,
                                        onStarStopChange = { startStop = it },
                                        navController = navController,
                                        pagerState = pagerState,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it },
                                    )


                                    is TimeExercise -> StartTimeSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as TimeExercise,
                                        startStop = startStop,
                                        onStarStopChange = { startStop = it },
                                        navController = navController,
                                        pagerState = pagerState,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it },
                                    )
                                }
                            } else {
                                when (exerciseList!![index]) {
                                    //TODO zamienic te obiekty na Cardy https://developer.android.com/develop/ui/compose/components/card
                                    is RepetitionExercise -> StartRepetitionSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as RepetitionExercise,
                                        startStop = false,
                                        onStarStopChange = { false },
                                        navController = navController,
                                        pagerState = pagerState,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it },
                                    )


                                    is TimeExercise -> StartTimeSeries(
                                        context = LocalContext.current,
                                        exercise = exerciseList[index] as TimeExercise,
                                        startStop = false,
                                        onStarStopChange = { false },
                                        navController = navController,
                                        pagerState = pagerState,
                                        endOfSeries = endOfSeries,
                                        onEndOfSeriesChange = { endOfSeries = it },
                                    )
                                }
                            }

                            StepProgressBar(
                                numberOfSteps = exerciseList.indices,
                                pagerState = pagerState
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StepProgressBar(numberOfSteps: IntRange, pagerState: PagerState) {


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
                            color = if (i < pagerState.currentPage) Smola
                            else if (i > pagerState.currentPage) InsideLevel1
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


