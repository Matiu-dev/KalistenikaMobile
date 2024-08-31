package pl.matiu.kalistenika.training

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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.training.model.TrainingModel
import pl.matiu.kalistenika.ui.theme.Beige
import pl.matiu.kalistenika.ui.theme.InsideLevel0Background
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
@ExperimentalFoundationApi
fun TrainingScreen(navController: NavController) {

    val context = LocalContext.current
    val trainingInternalStorageService: TrainingInternalStorageService = TrainingInternalStorageService()
    var trainingList: List<TrainingModel> =
        trainingInternalStorageService.loadTrainingToInternalStorage(LocalContext.current)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Column {
            Row {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(1),
                    contentPadding = PaddingValues(
                        horizontal = 25.dp,
                        vertical = 15.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    itemsIndexed(trainingList) { index, training ->
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
                                        trainingInternalStorageService.deleteTrainingFromInternalStorage(
                                            context,
                                            index
                                        )
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
            }
        }
    }
}

@Composable
fun AddTrainingButton( onClick: () -> Unit,) {

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
