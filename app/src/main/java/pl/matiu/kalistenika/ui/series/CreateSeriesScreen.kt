package pl.matiu.kalistenika.ui.series

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.viewModel.SeriesViewModel
import pl.matiu.kalistenika.viewModel.TrainingViewModel

@Composable
fun CreateSeries(
    navController: NavController,
    trainingId: Int?,
    context: Context,
    trainingName: String
) {

    val seriesViewModel: SeriesViewModel = viewModel()
    val listOfSeries = seriesViewModel.exerciseList.collectAsState()
    val numberOfExercise: Int = listOfSeries.value?.filter { it.trainingId == trainingId }?.size ?: 0

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1,
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {

            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                TimeSeries(
                    navController = navController,
                    trainingId = trainingId,
                    context = context,
                    numberOfExercise = numberOfExercise,
                    seriesViewModel = seriesViewModel,
                    trainingName = trainingName
                )
            }

            Row(modifier = Modifier.padding(vertical = 5.dp)) {
                RepetitiveSeries(
                    navController = navController,
                    trainingId = trainingId,
                    trainingName = trainingName,
                    context = context,
                    numberOfExercise = numberOfExercise,
                    seriesViewModel = seriesViewModel
                )
            }
        }
    }
}

@Composable
fun RepetitiveSeries(
    navController: NavController,
    trainingId: Int?,
    trainingName: String,
    context: Context,
    numberOfExercise: Int,
    seriesViewModel: SeriesViewModel
) {

    var expanded by remember { mutableStateOf(false) }

    Surface(
        color = InsideLevel1,
        modifier = Modifier
            .border(1.dp, Smola, RoundedCornerShape(8.dp))
            .background(InsideLevel2)
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Seria na liczbę powtórzeń",
                        color = Smola,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }

                OutlinedButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(text = if (!expanded) "Pokaż" else "Ukryj", color = Smola)
                }

            }

            Row(
                modifier = Modifier
                    .padding(if (expanded) 5.dp else 0.dp),
            ) {
                if (expanded) {
                    Surface(
                        color = Color.Transparent,
                        modifier = Modifier.padding(vertical = 5.dp)
                    ) {
                        Column {
                            RepetitiveSeriesOptions(
                                numberOfExercise = numberOfExercise,
                                context = context,
                                navController = navController,
                                trainingId = trainingId,
                                trainingName = trainingName,
                                seriesViewModel = seriesViewModel
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun RepetitiveSeriesOptions(
    numberOfExercise: Int,
    context: Context,
    navController: NavController,
    trainingId: Int?,
    trainingName: String,
    seriesViewModel: SeriesViewModel
) {
    var exerciseName by rememberSaveable { mutableStateOf("") }
    var numberOfSeries by rememberSaveable { mutableStateOf("") }
    var numberOfReps by rememberSaveable { mutableStateOf("") }
    var stopTimer by rememberSaveable { mutableStateOf(false) }
    var breakBetweenSeries by rememberSaveable { mutableStateOf("") }
    var exercisePositionInTraining by rememberSaveable { mutableIntStateOf(numberOfExercise + 1) }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
    ) {

        item {
            RowTextFieldElement(
                elementName = "Nazwa ćwiczenia",
                element = exerciseName,
                onElementChange = { exerciseName = it })
        }

        item {
            RowTextFieldElement(
                elementName = "Ilość serii(liczba)",
                element = numberOfSeries,
                onElementChange = { numberOfSeries = it })
        }

        item {
            RowTextFieldElement(
                elementName = "Przerwa między seriami(sekundy)",
                element = breakBetweenSeries,
                onElementChange = { breakBetweenSeries = it })
        }

        item {
            RowTextFieldElement(
                elementName = "Liczba powtórzeń w serii",
                element = numberOfReps,
                onElementChange = { numberOfReps = it })
        }

        item {
            RowCheckBoxElement(
                elementName = "Czy zatrzymać czas po serii?",
                element = stopTimer,
                onElementChange = { stopTimer = it })
        }

        item {
            RowExposedDropdownMenuBox(
                elementName = "Pozycja dla ćwiczenia",
                element = exercisePositionInTraining,
                onElementChange = { exercisePositionInTraining = it },
                numberOfExercise = numberOfExercise
            )
        }

        item {
            Row(
                modifier = Modifier.padding(vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        if (exerciseName.isNotBlank() &&
                            numberOfSeries.toIntOrNull() is Int &&
                            numberOfReps.toIntOrNull() is Int &&
                            breakBetweenSeries.toIntOrNull() is Int
                        ) {
                            seriesViewModel.addRepetitionSeries(
                                RepetitionExercise(
                                    repetitionExerciseName = exerciseName,
                                    numberOfRepetitionSeries = numberOfSeries.toInt(),
                                    numberOfReps = numberOfReps.toInt(),
                                    stopTimer = stopTimer,
                                    breakBetweenRepetitionSeries = breakBetweenSeries.toInt(),
                                    positionInTraining = exercisePositionInTraining - 1,
                                    trainingId = trainingId
                                )
                            )

                            navController.navigate("training/${trainingName}/${trainingId}")
                        } else {
                            Toast.makeText(context, "Popraw dane", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                ) {
                    Text(text = "Dodaj serię", color = Smola)
                }
            }
        }
    }
}

@Composable
fun TimeSeries(
    navController: NavController,
    trainingId: Int?,
    trainingName: String,
    context: Context,
    numberOfExercise: Int,
    seriesViewModel: SeriesViewModel
) {

    var expanded by remember {
        mutableStateOf(false)
    }

//    val extraPadding by remember(
//        if (expanded) 5.dp else 0.dp
//    )

    Surface(
        color = InsideLevel1,
        modifier = Modifier.border(1.dp, Smola, RoundedCornerShape(8.dp))
    ) {
        Column() {
            Row(
                modifier = Modifier
                    .padding(5.dp),
//                    .animateContentSize(
//                        animationSpec = spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy,
//                            stiffness = Spring.StiffnessLow
//                        ),
//                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = "Seria na czas",
                        color = Smola,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 5.dp)
                    )
                }


                OutlinedButton(
                    onClick = { expanded = !expanded },
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(text = if (!expanded) "Pokaż" else "Ukryj", color = Smola)
                }

            }

            Row(
                modifier = Modifier
                    .padding(if (expanded) 5.dp else 0.dp),
//                    .animateContentSize(
//                        animationSpec = spring(
//                            dampingRatio = Spring.DampingRatioMediumBouncy,
//                            stiffness = Spring.StiffnessLow
//                        )
//                    )
            ) {

                if (expanded) {
                    Surface(color = Color.Transparent) {
                        Column {
                            TimeSeriesOptions(
                                numberOfExercise = numberOfExercise,
                                navController = navController,
                                trainingId = trainingId,
                                trainingName = trainingName,
                                context = context,
                                seriesViewModel = seriesViewModel
                            )
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun TimeSeriesOptions(
    numberOfExercise: Int,
    navController: NavController,
    trainingId: Int?,
    trainingName: String,
    context: Context,
    seriesViewModel: SeriesViewModel
) {
    var exerciseName by rememberSaveable { mutableStateOf("") }
    var numberOfSeries by rememberSaveable { mutableStateOf("") }
    var breakBetweenSeries by rememberSaveable { mutableStateOf("") }
    var timeForSeries by rememberSaveable { mutableStateOf("") }
    var exercisePositionInTraining by rememberSaveable { mutableIntStateOf(numberOfExercise + 1) }

    Column {

        RowTextFieldElement(
            elementName = "Nazwa ćwiczenia",
            element = exerciseName,
            onElementChange = { exerciseName = it },
        )
        RowTextFieldElement(
            elementName = "Ilość serii(liczba)",
            element = numberOfSeries,
            onElementChange = { numberOfSeries = it })
        RowTextFieldElement(
            elementName = "Czas dla serii(sekundy)",
            element = timeForSeries,
            onElementChange = { timeForSeries = it })
        RowTextFieldElement(
            elementName = "Przerwa między seriami(sekundy)",
            element = breakBetweenSeries,
            onElementChange = { breakBetweenSeries = it })
        RowExposedDropdownMenuBox(
            elementName = "Pozycja dla ćwiczenia",
            element = exercisePositionInTraining,
            onElementChange = { exercisePositionInTraining = it },
            numberOfExercise = numberOfExercise
        )

        Button(
            onClick = {
                if (exerciseName.isNotBlank() &&
                    numberOfSeries.toIntOrNull() is Int &&
                    timeForSeries.toIntOrNull() is Int &&
                    breakBetweenSeries.toIntOrNull() is Int
                ) {
                    seriesViewModel.addTimeSeries(
                        TimeExercise(
                            timeExerciseName = exerciseName,
                            numberOfTimeSeries = numberOfSeries.toInt(),
                            timeForTimeSeries = timeForSeries.toInt(),
                            breakBetweenTimeSeries = breakBetweenSeries.toInt(),
                            positionInTraining = exercisePositionInTraining - 1,
                            trainingId = trainingId
                        )
                    )

                    navController.navigate("training/${trainingName}/${trainingId}")
                } else {
                    Toast.makeText(context, "Popraw dane", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
        ) {
            Text(text = "Dodaj serię", color = Smola)
        }
    }
}

@Composable
fun RowTextFieldElement(elementName: String, element: String, onElementChange: (String) -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = elementName,
            color = Smola,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        TextField(
            value = element,
            onValueChange = { onElementChange(it) },
            colors = TextFieldDefaults.colors(
                focusedTextColor = Smola,
                unfocusedTextColor = Smola,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                cursorColor = Smola,
                focusedIndicatorColor = Smola,
                unfocusedIndicatorColor = Smola,
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}

@Composable
fun RowCheckBoxElement(elementName: String, element: Boolean, onElementChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = elementName,
            color = Smola,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        Checkbox(
            checked = element,
            onCheckedChange = { onElementChange(it) },
            colors = CheckboxDefaults.colors(
                checkmarkColor = Smola,
                checkedColor = Color.Transparent,
                uncheckedColor = Smola
            ),
            modifier = Modifier.padding(horizontal = 5.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RowExposedDropdownMenuBox(
    elementName: String,
    element: Int,
    onElementChange: (Int) -> Unit,
    numberOfExercise: Int
) {

    var menuExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = elementName,
            color = Smola,
            modifier = Modifier.padding(horizontal = 5.dp)
        )

        ExposedDropdownMenuBox(
            expanded = menuExpanded,
            onExpandedChange = { menuExpanded = !menuExpanded }
        ) {
            TextField(
                value = "$element",
                onValueChange = {},
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Smola,
                    unfocusedTextColor = Smola,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    cursorColor = Smola,
                    focusedIndicatorColor = Smola,
                    unfocusedIndicatorColor = Smola,
                ),
                modifier = Modifier
                    .padding(horizontal = 5.dp)
                    .menuAnchor(),
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpanded) }
            )

            ExposedDropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                for (i in 1..numberOfExercise + 1) {
                    DropdownMenuItem(
                        text = { Text(text = "$i") },
                        onClick = {
                            onElementChange(i)
                            menuExpanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
                }
            }
        }

    }
}