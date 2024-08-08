package pl.matiu.kalistenika.training

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import pl.matiu.kalistenika.internalStorage.ExerciseInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.setOneSeries
import pl.matiu.kalistenika.training.model.RepetitionExercise
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.ui.theme.Beige
import pl.matiu.kalistenika.ui.theme.InsideLevel0Background
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.ui.theme.Wheat

@Composable
fun CreateSeries(
    navController: NavController,
    trainingId: Int?,
    context: Context
) {

    var exerciseName by rememberSaveable {
        mutableStateOf("")
    }

    var iloscSerii by rememberSaveable {
        mutableStateOf("")
    }

    //time series
    var breakBetweenSeries by rememberSaveable {
        mutableStateOf("")
    }

    var timeForSeries by rememberSaveable {
        mutableStateOf("")
    }
    //end time series

    //repetition series
    var numberOfReps by rememberSaveable {
        mutableStateOf("")
    }

    var stopTimer by rememberSaveable {
        mutableStateOf(false)
    }
    //end repetition series

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel0Background,
    ) {

        Column() {

            //time series

            TimeSeries(

                navController = navController,

                exerciseName = exerciseName,
                onExerciseNameChange = { exerciseName = it },

                iloscSerii = iloscSerii,
                onIloscSeriiChange = { iloscSerii = it },

                breakBetweenSeries = breakBetweenSeries,
                onBreakBetweenSeriesChange = { breakBetweenSeries = it },

                timeForSeries = timeForSeries,
                onTimeForSeriesChange = { timeForSeries = it },

                trainingId = trainingId,
                context = context
            )

            //repetitive series

            RepetitiveSeries(

                navController = navController,

                exerciseName = exerciseName,
                onExerciseNameChange = { exerciseName = it },

                iloscSerii = iloscSerii,
                onIloscSeriiChange = { iloscSerii = it },

                breakBetweenSeries = breakBetweenSeries,
                onBreakBetweenSeriesChange = { breakBetweenSeries = it },

                numberOfReps = numberOfReps,
                onNumberOfRepsChange = { nor -> numberOfReps = nor },

                stopTimer = stopTimer,
                onStopTimerChange = { st -> stopTimer = st },

                trainingId = trainingId,

                context = context
            )
        }
    }
}

@Composable
fun RepetitiveSeries(

    navController: NavController,

    exerciseName: String,
    onExerciseNameChange: (String) -> Unit,

    iloscSerii: String,
    onIloscSeriiChange: (String) -> Unit,

    breakBetweenSeries: String,
    onBreakBetweenSeriesChange: (String) -> Unit,

    numberOfReps: String,
    onNumberOfRepsChange: (String) -> Unit,

    stopTimer: Boolean,
    onStopTimerChange: (Boolean) -> Unit,

    trainingId: Int?,

    context: Context
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 5.dp else 0.dp, label = ""
    )

    Surface(
        color = InsideLevel1
    ) {

        Column {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    ),
                verticalAlignment = Alignment.CenterVertically
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
                    .padding(extraPadding)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {

                if (expanded) {
                    Surface(color = Color.Transparent) {
                        Column {
                            RepetitiveSeriesOptions(
                                exerciseName = exerciseName,
                                iloscSerii = iloscSerii,
                                numberOfReps = numberOfReps,
                                breakBetweenSeries = breakBetweenSeries,
                                stopTimer = stopTimer,

                                onExerciseNameChange = onExerciseNameChange,
                                onIloscSeriiChange = onIloscSeriiChange,
                                onNumberOfRepsChange = onNumberOfRepsChange,
                                onbreakBetweenSeriesChange = onBreakBetweenSeriesChange,
                                onStopTimerChange = onStopTimerChange
                            )

                            Button(
                                onClick = {
//                                    setOneSeries(
//                                        RepetitionExercise(
//                                            exerciseName,
//                                            iloscSerii.toInt(),
//                                            numberOfReps.toInt(),
//                                            stopTimer,
//                                            breakBetweenSeries.toInt(),
//                                            trainingId
//                                        )
//                                    )

                                    ExerciseInternalStorageService().saveRepetitionExerciseToInternalStorage(
                                        context,
                                        RepetitionExercise(
                                            1,
                                            exerciseName,
                                            iloscSerii.toInt(),
                                            numberOfReps.toInt(),
                                            stopTimer,
                                            breakBetweenSeries.toInt(),
                                            trainingId
                                        )
                                    )

                                    navController.navigate("training/${trainingId}")
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
        }


    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun RepetitiveSeriesOptions(
    exerciseName: String,
    onExerciseNameChange: (String) -> Unit,

    iloscSerii: String,
    onIloscSeriiChange: (String) -> Unit,

    numberOfReps: String,
    onNumberOfRepsChange: (String) -> Unit,

    stopTimer: Boolean,
    onStopTimerChange: (Boolean) -> Unit,

    breakBetweenSeries: String,
    onbreakBetweenSeriesChange: (String) -> Unit
) {

    Column {

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Nazwa ćwiczenia",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )


            TextField(
                value = exerciseName,
                onValueChange = onExerciseNameChange,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Smola,
                    cursorColor = Smola,
                    focusedIndicatorColor = Smola,
                    unfocusedIndicatorColor = Smola,
                    unfocusedTextColor = Smola
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )

        }

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Ilość serii(liczba)",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )


            TextField(
                value = iloscSerii,
                onValueChange = onIloscSeriiChange,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Smola,
                    cursorColor = Smola,
                    focusedIndicatorColor = Smola,
                    unfocusedIndicatorColor = Smola,
                    unfocusedTextColor = Smola
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )

        }

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Przerwa między seriami(sekundy)",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            TextField(
                value = breakBetweenSeries,
                onValueChange = onbreakBetweenSeriesChange,
                colors = TextFieldDefaults.textFieldColors(
                    containerColor = Color.Transparent,
                    focusedTextColor = Smola,
                    cursorColor = Smola,
                    focusedIndicatorColor = Smola,
                    unfocusedIndicatorColor = Smola,
                    unfocusedTextColor = Smola
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Liczba powtórzeń w serii",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            TextField(
                value = numberOfReps,
                onValueChange = onNumberOfRepsChange,
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

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Czy zatrzymać czas po serii?",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )

            Checkbox(checked = stopTimer,
                onCheckedChange = onStopTimerChange,
                colors = CheckboxDefaults.colors(
                    checkmarkColor = Smola,
                    checkedColor = Color.Transparent,
                    uncheckedColor = Smola
                ),
                modifier = Modifier.padding(horizontal = 5.dp)
            )
        }
    }
}

@Composable
fun TimeSeries(

    navController: NavController,

    exerciseName: String,
    onExerciseNameChange: (String) -> Unit,

    iloscSerii: String,
    onIloscSeriiChange: (String) -> Unit,

    breakBetweenSeries: String,
    onBreakBetweenSeriesChange: (String) -> Unit,

    timeForSeries: String,
    onTimeForSeriesChange: (String) -> Unit,

    trainingId: Int?,

    context: Context,
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    val extraPadding by animateDpAsState(
        targetValue = if (expanded) 5.dp else 0.dp, label = ""
    )

    Surface(
        color = InsideLevel1,
        modifier = Modifier.padding(vertical = 5.dp)
    ) {

        Column() {
            Row(
                modifier = Modifier
                    .padding(5.dp)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    ),
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
                    .padding(extraPadding)
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioMediumBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
            ) {

                if (expanded) {
                    Surface(color = Color.Transparent) {
                        Column {
                            TimeSeriesOptions(
                                exerciseName = exerciseName,
                                iloscSerii = iloscSerii,
                                timeForSeries = timeForSeries,
                                breakBetweenSeries = breakBetweenSeries,

                                onExerciseNameChange = onExerciseNameChange,
                                onIloscSeriiChange = onIloscSeriiChange,
                                onTimeForSeriesChange = onTimeForSeriesChange,
                                onbreakBetweenSeriesChange = onBreakBetweenSeriesChange,
                            )

                            Button(
                                onClick = {
//                                    setOneSeries(
//                                        TimeExercise(
//                                            exerciseName,
//                                            iloscSerii.toInt(),
//                                            timeForSeries.toInt(),
//                                            breakBetweenSeries.toInt(),
//                                            trainingId
//                                        )
//                                    )

                                    ExerciseInternalStorageService().saveTimeExerciseToInternalStorage(
                                        context,
                                        TimeExercise(
                                            1,
                                            exerciseName,
                                            iloscSerii.toInt(),
                                            timeForSeries.toInt(),
                                            breakBetweenSeries.toInt(),
                                            trainingId
                                        )
                                    )

                                    navController.navigate("training/${trainingId}")
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
        }


    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TimeSeriesOptions(
    exerciseName: String,
    iloscSerii: String,
    timeForSeries: String,
    breakBetweenSeries: String,

    onExerciseNameChange: (String) -> Unit,
    onIloscSeriiChange: (String) -> Unit,
    onTimeForSeriesChange: (String) -> Unit,
    onbreakBetweenSeriesChange: (String) -> Unit
) {

    Column {

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {

            Text(
                text = "Nazwa ćwiczenia",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )


            TextField(
                value = exerciseName,
                onValueChange = onExerciseNameChange,
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

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Ilość serii(liczba)",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )


            TextField(
                value = iloscSerii,
                onValueChange = onIloscSeriiChange,
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

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Czas dla serii(sekundy)",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            TextField(
                value = timeForSeries,
                onValueChange = onTimeForSeriesChange,
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

        Row(modifier = Modifier.padding(vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Przerwa między seriami(sekundy)",
                color = Smola,
                modifier = Modifier.padding(horizontal = 5.dp)
            )
            TextField(
                value = breakBetweenSeries,
                onValueChange = onbreakBetweenSeriesChange,
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
}

//            Row(modifier = Modifier.background(Beige)
//                .fillMaxWidth(),
//                verticalAlignment = Alignment.CenterVertically) {
//
//                TextButton(
//                    onClick = { navController.navigate("training/${trainingId}") },
//                ) {
//                    Icon(
//                        painter = rememberVectorPainter(image = Icons.Filled.ArrowBack),
//                        contentDescription = "play",
//                        tint = Smola,
//                    )
//                }
//
//                Text(
//                    text = "Kreator serii",
//                    fontSize = 50.sp,
//                    color = Wheat,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.fillMaxWidth()
//                        .weight(1f)
//                )
//            }
