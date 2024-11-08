package pl.matiu.kalistenika.composable.history

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
fun HistoryDetailsScreen(date: String) {

    val timeExercise = remember {
        mutableStateOf<List<TimeExercise>>(emptyList())
    }

    RealTimeDatabaseService().loadDataHistoryTimeExercise(date = date) { history ->
        timeExercise.value = history
    }

    val repetitionExercise = remember {
        mutableStateOf<List<RepetitionExercise>>(emptyList())
    }

    RealTimeDatabaseService().loadDataHistoryRepetitionExercise(date = date) { history ->
        repetitionExercise.value = history
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    text = "Treningi wykonane w dniu: $date"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start
            ) {
                Divider(
                    color = Smola,
                    thickness = 2.dp
                )
            }


            LazyColumn(modifier = Modifier
                .fillMaxWidth()) {
                items(timeExercise.value + repetitionExercise.value) { exercise ->


                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {

                        when (exercise) {
                            is RepetitionExercise -> {
                                if(exercise.repetitionExerciseName != "") {
                                    RepetitionExerciseList(exercise = exercise)
                                }

                                Log.d("exercise", exercise.toString())
                            }

                            is TimeExercise -> {
                                if(exercise.timeExerciseName != "") {
                                    TimeExerciseList(exercise = exercise)
                                }
                                Log.d("exercise", exercise.toString())
                            }
                        }
                    }

                }
            }
        }

    }
}

@Composable
fun RepetitionExerciseList(exercise: RepetitionExercise) {

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Nazwa ćwiczenia: ${exercise.repetitionExerciseName}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Liczba serii: ${exercise.numberOfRepetitionSeries}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Liczba powtórzeń: ${exercise.numberOfReps}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Czy zatrzymany czas: ${exercise.stopTimer}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Przerwa między seriami: ${exercise.breakBetweenRepetitionSeries}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Pozycja w treningu: ${exercise.positionInTraining}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Id treningu: ${exercise.trainingId}")
    }

    Divider(
        color = Smola,
        thickness = 2.dp
    )
}

@Composable
fun TimeExerciseList(exercise: TimeExercise) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Nazwa ćwiczenia: ${exercise.timeExerciseName}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Liczba serii: ${exercise.numberOfTimeSeries}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Czas dla serii: ${exercise.timeForTimeSeries}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Przerwa między seriami: ${exercise.breakBetweenTimeSeries}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Pozycja w treningu: ${exercise.positionInTraining}")
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Text(text = "Id treningu: ${exercise.trainingId}")
    }

    Divider(
        color = Smola,
        thickness = 2.dp
    )
}
