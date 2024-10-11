package pl.matiu.kalistenika.ui

import android.content.Context
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
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
fun RepetitionExerciseEditScreen(
    navigator: NavController,
    exercise: RepetitionExercise?,
    trainingId: Int,
    numberOfExercise: Int
) {
    var exerciseName by remember {
        mutableStateOf(exercise?.repetitionExerciseName)
    }

    var numberOfSeries by remember {
        mutableStateOf(exercise?.numberOfRepetitionSeries.toString())
    }

    var numberOfReps by remember {
        mutableStateOf(exercise?.numberOfReps.toString())
    }

    var stopTimer by remember {
        mutableStateOf(exercise?.stopTimer)
    }

    var breakBetweenSeries by remember {
        mutableStateOf(exercise?.breakBetweenRepetitionSeries.toString())
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
                .border(1.dp, Smola, RoundedCornerShape(8.dp)).fillMaxWidth()
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
                        if (exercise != null) {
                            stopTimer?.let {
                                RepetitionExercise(
                                    exercise.repetitionExerciseId,
                                    exerciseName.toString(),
                                    numberOfSeries.toInt(),
                                    numberOfReps.toInt(),
                                    it,
                                    breakBetweenSeries.toInt(),
                                    exercisePositionInTraining - 1,
                                    trainingId
                                )
                            }?.let {
                                ExerciseDatabaseService().updateRepetitionSeries(
                                    it
                                )
                            }
                        }
                        navigator.navigate(route = MainRoutes.Training.destination + "/${trainingId}")
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeExerciseEditScreen(
    navigator: NavController,
    context: Context,
    exercise: TimeExercise?,
    trainingId: Int,
    numberOfExercise: Int
) {

    var exerciseName by remember {
        mutableStateOf(exercise?.timeExerciseName)
    }

    var numberOfSeries by remember {
        mutableStateOf(exercise?.numberOfTimeSeries.toString())
    }

    var timeForSeries by remember {
        mutableStateOf(exercise?.timeForTimeSeries.toString())
    }

    var breakBetweenSeries by remember {
        mutableStateOf(exercise?.breakBetweenTimeSeries.toString())
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
                .border(1.dp, Smola, RoundedCornerShape(8.dp)).fillMaxWidth()
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

                exercise?.timeExerciseName?.let {
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

                exercise?.numberOfTimeSeries?.let {
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

                exercise?.timeForTimeSeries?.let {
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

                exercise?.breakBetweenTimeSeries?.let {
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
                        if (exercise != null) {
                            ExerciseDatabaseService().updateTimeSeries(
                                TimeExercise(
                                    exercise.timeExerciseId,
                                    exerciseName.toString(),
                                    numberOfSeries.toInt(),
                                    timeForSeries.toInt(),
                                    breakBetweenSeries.toInt(),
                                    exercisePositionInTraining - 1,
                                    trainingId
                                )
                            )
                        }
                        navigator.navigate(route = MainRoutes.Training.destination + "/${trainingId}")
                    },
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                ) {
                    Text(text = "Modyfikuj", color = Smola)
                }
            }
        }
    }
}


