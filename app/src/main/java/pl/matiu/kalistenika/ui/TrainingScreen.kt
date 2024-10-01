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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.Routes
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
@ExperimentalFoundationApi
fun TrainingScreen(navController: NavController) {

    val trainingViewModel: TrainingViewModel = viewModel()
    val trainingList2 by trainingViewModel.trainingList.collectAsState()

    for (training in trainingList2!!) {
        Log.d("dziala czy nie?", "${training.trainingId} ${training.name} ")
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Column {
            Row {
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
                                        trainingViewModel.deleteTraining(training.trainingId)
                                        navController.navigate(Routes.Training.destination)
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
            }
        }
    }
}

@Composable
fun AddTrainingButton(onClick: () -> Unit) {

    FloatingActionButton(
        onClick = {
            onClick()
        },
        containerColor = InsideLevel2,
        contentColor = Smola
    ) {
        Icon(Icons.Filled.Add, "add exercise")
    }
}
