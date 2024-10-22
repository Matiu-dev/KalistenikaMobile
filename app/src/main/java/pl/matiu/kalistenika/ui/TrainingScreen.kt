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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.testowa.dialog.DialogController
import pl.matiu.testowa.dialog.DialogEnum
import pl.matiu.testowa.dialog.ShowTrainingDialog

@Composable
@ExperimentalFoundationApi
fun TrainingScreen(navController: NavController) {

    val trainingViewModel: TrainingViewModel = viewModel()
    val trainingList2 by trainingViewModel.trainingList.collectAsState()

    val clickedTraining = remember { mutableIntStateOf(-1) }
    val showDialog = remember { mutableStateOf(false) }

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

                    if (trainingList2 != null && trainingList2!!.isNotEmpty()) {

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
                                        onLongClick = {
                                            showDialog.value = true
                                            clickedTraining.intValue = trainingList2!!.filter { it.trainingId == training.trainingId }.get(0).trainingId
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

        if(showDialog.value) {
            ShowTrainingDialog(
                dialogResponse = DialogController().showDialog(DialogEnum.DELETE_DIALOG,
                    trainingList2!!.filter { it.trainingId ==  clickedTraining.intValue}[0]),
                showDialog = showDialog.value,
                onShowDialogChange = { showDialog.value = it },
                navController = navController
            )
        }
    }
}

@Composable
fun AddTrainingButton(navController: NavController) {

    val showDialog = remember { mutableStateOf(false) }

    FloatingActionButton(
        onClick = {
            showDialog.value = true
        },
        containerColor = InsideLevel2,
        contentColor = Smola
    ) {
        Icon(Icons.Filled.Add, "add exercise")
    }

    if(showDialog.value) {
        ShowTrainingDialog(
            dialogResponse = DialogController().showDialog(DialogEnum.CREATE_DIALOG,
                TrainingModel(1,"aaa")
            ),
            showDialog = showDialog.value,
            onShowDialogChange = { showDialog.value = it },
            navController = navController
        )
    }
}
