package pl.matiu.kalistenika.ui

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.myViewModel.TrainingViewModel
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.trainingModel.TrainingModel
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
@ExperimentalFoundationApi
fun TrainingScreen(navController: NavController) {

//    val context = LocalContext.current
//    val trainingInternalStorageService: TrainingInternalStorageService =
//        TrainingInternalStorageService()
//    var trainingList: List<TrainingModel> =
//        trainingInternalStorageService.loadTrainingToInternalStorage(LocalContext.current)

    val trainingViewModel: TrainingViewModel = viewModel()
    val trainingList2 by trainingViewModel.trainingList.collectAsState()

//    LaunchedEffect(null) {
//        trainingViewModel.addTraining("testowy trening")
//    }

    for (training in trainingList2!!) {
        Log.d("dziala czy nie?", "${training.trainingId} ${training.name} ")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Column {
            Row {
//                LazyVerticalGrid(
//                    columns = GridCells.Fixed(1),
//                    contentPadding = PaddingValues(
//                        horizontal = 25.dp,
//                        vertical = 15.dp
//                    ),
//                    verticalArrangement = Arrangement.spacedBy(8.dp)
//                ) {
//
//                    itemsIndexed(trainingList) { index, training ->
//                        Column(
//
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(56.dp)
//                                .background(InsideLevel1, RoundedCornerShape(8.dp))
//                                .border(1.dp, Smola, RoundedCornerShape(8.dp))
//                                .combinedClickable(
//                                    onClick = {
//                                        navController.navigate("training/${training.trainingId}")
//                                    },
//                                    onDoubleClick = {
//                                        trainingInternalStorageService.deleteTrainingFromInternalStorage(
//                                            context,
//                                            index
//                                        )
//                                        navController.navigate(Training.route)
//                                    }
//                                ),
//                            verticalArrangement = Arrangement.Center,
//                            horizontalAlignment = Alignment.CenterHorizontally
//                        ) {
//                            Text(
//                                text = training.name,
//                                color = Smola
//                            )
//                        }
//                    }
//                }

                //test
                LazyColumn(
                    contentPadding = PaddingValues(
                        horizontal = 25.dp,
                        vertical = 15.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(trainingList2!!) { index, training ->
                        Column(

                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(InsideLevel1, RoundedCornerShape(8.dp))
                                .border(1.dp, Smola, RoundedCornerShape(8.dp))
                                .combinedClickable(
                                    onClick = {
                                        navController.navigate("training/${training.trainingId}")
                                    },
                                    onDoubleClick = {
//                                        trainingInternalStorageService.deleteTrainingFromInternalStorage(
//                                            context,
//                                            index
//                                        )
                                        trainingViewModel.deleteTraining(training.trainingId)
                                        navController.navigate(Training.route)
                                    }
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = training.name,
                                color = Smola
                            )
                        }
                    }
                }
                //test
            }
        }
    }
}

@Composable
fun AddTrainingButton(onClick: () -> Unit) {

    FloatingActionButton(
        onClick = {
//            InternalStorageService().saveTrainingToInternalStorage(context)
//            navController.navigate(Training.route)
            onClick()
        },
        containerColor = InsideLevel2,
        contentColor = Smola
    ) {
        Icon(Icons.Filled.Add, "add exercise")
    }
}
