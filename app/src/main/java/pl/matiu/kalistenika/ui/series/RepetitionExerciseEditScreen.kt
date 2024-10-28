package pl.matiu.kalistenika.ui.series

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.viewModel.SeriesViewModel

@Composable
fun RepetitionExerciseEditScreen(repetitionExercise: RepetitionExercise, numberOfExercise: Int, trainingName: String,
                                 trainingId: Int, navigator: NavController, seriesViewModel: SeriesViewModel) {
    var exerciseName by remember {
        mutableStateOf(repetitionExercise.repetitionExerciseName)
    }

    var numberOfSeries by remember {
        mutableStateOf(repetitionExercise.numberOfRepetitionSeries.toString())
    }

    var numberOfReps by remember {
        mutableStateOf(repetitionExercise.numberOfReps.toString())
    }

    var stopTimer by remember {
        mutableStateOf(repetitionExercise.stopTimer)
    }

    var breakBetweenSeries by remember {
        mutableStateOf(repetitionExercise.breakBetweenRepetitionSeries.toString())
    }

    var exercisePositionInTraining by rememberSaveable { mutableIntStateOf(numberOfExercise) }

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

            exerciseName?.let {
                RowTextFieldElement(
                    "Nazwa ćwiczenia",
                    element = it,
                    onElementChange = { exerciseName = it })
            }

            RowTextFieldElement(
                "Ilość serii(liczba)",
                element = numberOfSeries,
                onElementChange = { numberOfSeries = it })

            RowTextFieldElement(
                "Przerwa między seriami(sekundy)",
                element = breakBetweenSeries,
                onElementChange = { breakBetweenSeries = it })

            RowTextFieldElement(
                "Liczba powtórzeń w serii",
                element = numberOfReps,
                onElementChange = { numberOfReps = it })


            stopTimer?.let {
                RowCheckBoxElement(
                    elementName = "Czy zatrzymać czas po serii?",
                    element = stopTimer!!,
                    onElementChange = { stopTimer = it })
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
                        stopTimer?.let {
                            RepetitionExercise(
                                repetitionExercise.repetitionExerciseId,
                                exerciseName.toString(),
                                numberOfSeries.toInt(),
                                numberOfReps.toInt(),
                                it,
                                breakBetweenSeries.toInt(),
                                exercisePositionInTraining - 1,
                                trainingId
                            )
                        }?.let {
                            seriesViewModel.editRepetitionSeries(
                                it
                            )
                        }
                        navigator.navigate(route = MainRoutes.Training.destination + "/${trainingName}" + "/${trainingId}")
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