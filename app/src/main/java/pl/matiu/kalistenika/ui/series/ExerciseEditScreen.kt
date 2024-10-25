package pl.matiu.kalistenika.ui.series

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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.viewModel.SeriesViewModel
import pl.matiu.kalistenika.viewModel.TrainingViewModel

@Composable
fun ExerciseEditScreen(
    navigator: NavController,
    trainingId: Int,
    exerciseId: Int
) {
    val trainingViewModel: TrainingViewModel = viewModel()
    val trainingList = trainingViewModel.trainingList.collectAsState()
    val seriesViewModel: SeriesViewModel = viewModel()
    val exerciseList = seriesViewModel.exerciseList.collectAsState()
    val exercise: SeriesInterface = exerciseList.value?.filter { when(it) {
        is RepetitionExercise -> {
            it.repetitionExerciseId == exerciseId
        }

        is TimeExercise -> {
            it.timeExerciseId == exerciseId
        }

        else -> {false}
    } }?.get(0) ?: RepetitionExercise()

    val numberOfExercise = exerciseList.value?.filter { it.trainingId == trainingId }?.size

    when(exercise) {
        is RepetitionExercise -> {
            numberOfExercise?.let { RepetitionExerciseEditScreen(repetitionExercise = exercise,
                numberOfExercise = it, trainingId = trainingId, navigator = navigator,
                seriesViewModel = seriesViewModel, trainingName = trainingList.value!!.filter { it.trainingId == trainingId }.get(0).name) }
        }

        is TimeExercise -> {
            numberOfExercise?.let { TimeExerciseEditScreen(timeExercise = exercise,
                numberOfExercise = it, trainingId = trainingId, navigator = navigator,
                seriesViewModel = seriesViewModel, trainingName = trainingList.value!!.filter { it.trainingId == trainingId }.get(0).name) }
        }
    }
}