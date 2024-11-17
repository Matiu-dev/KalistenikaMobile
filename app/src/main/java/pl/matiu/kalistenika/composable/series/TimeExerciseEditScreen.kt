package pl.matiu.kalistenika.composable.series

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.viewModel.SeriesViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeExerciseEditScreen(timeExercise: TimeExercise, numberOfExercise: Int, trainingName: String,
                           trainingId: Int, navigator: NavController, seriesViewModel: SeriesViewModel
) {
    var exerciseName by remember {
        mutableStateOf(timeExercise.timeExerciseName)
    }

    var numberOfSeries by remember {
        mutableStateOf(timeExercise.numberOfTimeSeries.toString())
    }

    var timeForSeries by remember {
        mutableStateOf(timeExercise.timeForTimeSeries.toString())
    }

    var breakBetweenSeries by remember {
        mutableStateOf(timeExercise.breakBetweenTimeSeries.toString())
    }

    var exercisePositionInTraining by rememberSaveable { mutableIntStateOf(numberOfExercise) }

    var isClicked by remember { mutableStateOf(false) }
    if(isClicked) {
        val isLodaing = seriesViewModel.isLoading.collectAsState()

        if(!isLodaing.value) {
            navigator.navigate(route = MainRoutes.Training.destination + "/${trainingName}" + "/${trainingId}")
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = InsideLevel1
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .border(1.dp, Smola, RoundedCornerShape(8.dp))
                .fillMaxWidth()
        ) {


            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "Nazwa ćwiczenia",
                    color = Smola,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                TextField(
                    value = exerciseName.toString(),
                    onValueChange = { exerciseName = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Smola,
                        focusedIndicatorColor = Smola,
                        unfocusedIndicatorColor = Smola,
                        focusedTextColor = Smola,
                        unfocusedTextColor = Smola
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

            }

            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Ilość serii(liczba)",
                    color = Smola,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                TextField(
                    value = numberOfSeries,
                    onValueChange = { numberOfSeries = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Smola,
                        focusedIndicatorColor = Smola,
                        unfocusedIndicatorColor = Smola,
                        focusedTextColor = Smola,
                        unfocusedTextColor = Smola
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )
            }

            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Czas dla serii(sekundy)",
                    color = Smola,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                TextField(
                    value = timeForSeries,
                    onValueChange = { timeForSeries = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Smola,
                        focusedIndicatorColor = Smola,
                        unfocusedIndicatorColor = Smola,
                        focusedTextColor = Smola,
                        unfocusedTextColor = Smola
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

            }

            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Przerwa między seriami(sekundy)",
                    color = Smola,
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

                TextField(
                    value = breakBetweenSeries,
                    onValueChange = { breakBetweenSeries = it },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.Transparent,
                        cursorColor = Smola,
                        focusedIndicatorColor = Smola,
                        unfocusedIndicatorColor = Smola,
                        focusedTextColor = Smola,
                        unfocusedTextColor = Smola
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp)
                )

            }

            RowExposedDropdownMenuBox(
                elementName = "Pozycja dla ćwiczenia",
                element = exercisePositionInTraining,
                onElementChange = { exercisePositionInTraining = it },
                numberOfExercise = numberOfExercise - 1
            )

            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        seriesViewModel.editTimeSeries(
                            TimeExercise(
                                timeExercise.timeExerciseId,
                                exerciseName.toString(),
                                numberOfSeries.toInt(),
                                timeForSeries.toInt(),
                                breakBetweenSeries.toInt(),
                                exercisePositionInTraining - 1,
                                trainingId
                            )
                        )
                        isClicked = true
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                ) {
                    Text(text = "Modyfikuj", color = Smola)
                }
            }
        }
    }
}