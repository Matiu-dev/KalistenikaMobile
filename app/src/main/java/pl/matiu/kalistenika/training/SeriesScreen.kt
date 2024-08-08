package pl.matiu.kalistenika.training

import StartRepetitionSeries
import android.content.Context
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.matiu.kalistenika.getSeries
import pl.matiu.kalistenika.internalStorage.ExerciseInternalStorageService
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.RepetitionExercise
import pl.matiu.kalistenika.training.model.SeriesInterface
import pl.matiu.kalistenika.training.timeExercise.StartTimeSeries
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.training.model.TrainingModel
import pl.matiu.kalistenika.ui.theme.Beige
import pl.matiu.kalistenika.ui.theme.InsideLevel0Background
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

//progression indicator do linii miedzy kulkami
//bottom albo side sheets - do menu dolnego lub boczbnego
@Composable
fun SeriesScreen(navController: NavController,
                 trainingId: Int?) {

    var addTraining by rememberSaveable { mutableStateOf(false) }
    var actualTraining by rememberSaveable { mutableIntStateOf(0) }
    var startStop by rememberSaveable { mutableStateOf(false) }

    val exerciseInternalStorageService: ExerciseInternalStorageService = ExerciseInternalStorageService()
    var timeExerciseList: List<SeriesInterface> =
        exerciseInternalStorageService.loadTimeExerciseToInternalStorage(LocalContext.current).filter { v -> v.trainingId == trainingId }

    var repetitionExerciseList: List<SeriesInterface> =
        exerciseInternalStorageService.loadRepetitionExerciseToInternalStorage(LocalContext.current).filter { v -> v.trainingId == trainingId }

    var exerciseList: List<SeriesInterface> = timeExerciseList + repetitionExerciseList
//    var showBottomSheet by remember { mutableStateOf(true) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel0Background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp))
        {


            if (!addTraining) {

//                TopBar(navController = navController,
//                    trainingId = trainingId,
//                    addTraining = addTraining,
//                    onAddTrainingChange = { addTraining = it }
//                )

                //buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(InsideLevel1),
                    horizontalArrangement = Arrangement.Center,
                ) {

                    Button(
                        onClick = { if (actualTraining != 0) actualTraining-- },
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
                        onClick = { if (actualTraining != exerciseList.size - 1) actualTraining++ },
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

                //items
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                ) {
                    itemsIndexed( timeExerciseList + repetitionExerciseList ) { index, series ->

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .background(InsideLevel1),
                            verticalAlignment = Alignment.CenterVertically,

                        ) {

                            //przycisk - lewo

                            Column(
                                modifier = Modifier.weight(1f),
                                horizontalAlignment = Alignment.CenterHorizontally

                            ) {

                                if (index == actualTraining) {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(Smola)
                                            .border(BorderStroke(2.dp, Smola))
                                    )
                                } else {
                                    Box(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .clip(CircleShape)
                                            .background(Color.Transparent)
                                            .border(2.dp, Smola, CircleShape)
                                            .padding(1.dp)
                                    )
                                }

                            }

                            //prawo
                            Column(
                                modifier = Modifier.weight(2f),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                if (index == actualTraining) {
                                    when (series) {

                                        is RepetitionExercise -> StartRepetitionSeries(
                                            exercise = series,
                                            startStop = startStop,
                                            onStarStopChange = { startStop = it },
                                            navController = navController,
                                            context = LocalContext.current
                                        )

                                        is TimeExercise -> StartTimeSeries(
                                            context = LocalContext.current,
                                            exercise = series,
                                            startStop = startStop,
                                            onStarStopChange = { startStop = it },
                                            navController = navController
                                        )
                                    }
                                } else {
                                    when (series) {
                                        //TODO zamienic te obiekty na Cardy https://developer.android.com/develop/ui/compose/components/card
                                        is RepetitionExercise -> StartRepetitionSeries(
                                            exercise = series,
                                            startStop = false,
                                            onStarStopChange = { false },
                                            navController = navController,
                                            context = LocalContext.current
                                        )

                                        is TimeExercise -> StartTimeSeries(
                                            context = LocalContext.current,
                                            exercise = series,
                                            false,
                                            { false },
                                            navController = navController)
                                    }
                                }

                            }
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

//@Composable
//fun TopBar(
//    navController: NavController,
//    trainingId: Int?,
//    addTraining: Boolean,
//    onAddTrainingChange: (Boolean) -> Unit
//) {
//
//    var showBottomSheet by remember { mutableStateOf(false) }
//
//    if (showBottomSheet) {
////        SeriesBottomSheet(
////            showBottomSheet = showBottomSheet,
////            addTraining = addTraining,
////
////            onShowBottomSheetChange = { showBottomSheet = it },
////            onAddTrainingChange = onAddTrainingChange
////        )
//    }
//
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(Beige),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//
//        TextButton(
//            onClick = { navController.navigate(Training.route) },
//        ) {
//            Icon(
//                painter = rememberVectorPainter(image = Icons.Filled.ArrowBack),
//                contentDescription = "play",
//                tint = Smola,
//            )
//        }
//
//        Text(
//            text = "Trening $trainingId",
//            fontSize = 50.sp,
//            color = Wheat,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        )
//
//        TextButton(
//            onClick = { showBottomSheet = !showBottomSheet }
//        ) {
//            Icon(
//                painter = rememberVectorPainter(image = Icons.Filled.AddCircle),
//                contentDescription = "play",
//                tint = Smola
//            )
//        }
//    }
//}

//to-do
//                                Divider(
//                                    color = Smola,
//                                    modifier = Modifier
//                                        .fillMaxHeight()
//                                        .width(1.dp)
//                                )
