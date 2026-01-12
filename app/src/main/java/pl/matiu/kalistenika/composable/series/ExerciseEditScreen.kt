package pl.matiu.kalistenika.composable.series

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.viewModel.SeriesViewModel
import pl.matiu.kalistenika.viewModel.TrainingViewModel

@Composable
fun RepetitionExerciseEditScreen(
    navigator: NavController,
    trainingId: Int,
    exerciseId: Int,
    onUpdateTopBar: (title: String, preview: String, icon: Boolean, button: String) -> Unit
) {

    val trainingViewModel: TrainingViewModel = hiltViewModel<TrainingViewModel>()
    val trainingList = trainingViewModel.trainingList.collectAsState()
    val isTrainingLoading = trainingViewModel.isTrainingLoading.collectAsState()

    val seriesViewModel: SeriesViewModel = hiltViewModel<SeriesViewModel>()
    val exerciseList = seriesViewModel.exerciseList.collectAsState()

    seriesViewModel.getRepetitionSeriesByExerciseId(exerciseId = exerciseId)

    var numberOfExercise = 0
    var trainingName = ""

    trainingList.value.let {
        numberOfExercise = exerciseList.value?.filter { it2 -> it2.trainingId == trainingId }?.size ?: 0
        trainingName = it?.filter { it2 -> it2.trainingId == trainingId }?.get(0)?.name ?: ""
    }

    LaunchedEffect(Unit) {
        onUpdateTopBar(
            AlternativeRoutes.EditRepetitionSeries.topBarTitle,
            AlternativeRoutes.EditRepetitionSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}",
            AlternativeRoutes.EditRepetitionSeries.isNavigationIcon,
            AlternativeRoutes.EditRepetitionSeries.addButton
        )
    }

    if(!isTrainingLoading.value) {
            seriesViewModel.repetitionExercise.collectAsState().value?.let { it1 ->
                RepetitionExerciseEditScreen(repetitionExercise = it1,
                    numberOfExercise = numberOfExercise,
                    trainingId = trainingId,
                    navigator = navigator,
                    seriesViewModel = seriesViewModel,
                    trainingName = trainingName
                )
            }
    }
}

@Composable
fun TimeExerciseEditScreen(
    navigator: NavController,
    trainingId: Int,
    exerciseId: Int,
    onUpdateTopBar: (title: String, preview: String, icon: Boolean, button: String) -> Unit
) {
    val trainingViewModel: TrainingViewModel = hiltViewModel<TrainingViewModel>()
    val trainingList = trainingViewModel.trainingList.collectAsState()
    val isTrainingLoading = trainingViewModel.isTrainingLoading.collectAsState()

    val seriesViewModel: SeriesViewModel = hiltViewModel<SeriesViewModel>()
    val exerciseList = seriesViewModel.exerciseList.collectAsState()

    seriesViewModel.getTimeSeriesByExerciseId(exerciseId = exerciseId)

    var numberOfExercise = 0
    var trainingName = ""

    trainingList.value.let {
        numberOfExercise = exerciseList.value?.filter { it2 -> it2.trainingId == trainingId }?.size ?: 0
        trainingName = it?.filter { it2 -> it2.trainingId == trainingId }?.get(0)?.name ?: ""
    }

    LaunchedEffect(Unit) {
        onUpdateTopBar(
            AlternativeRoutes.EditTimeSeries.topBarTitle,
            AlternativeRoutes.EditTimeSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}",
            AlternativeRoutes.EditTimeSeries.isNavigationIcon,
            AlternativeRoutes.EditTimeSeries.addButton
        )
    }

    if(!isTrainingLoading.value) {
            seriesViewModel.timeExercise.collectAsState().value?.let { it1 ->
                TimeExerciseEditScreen(timeExercise = it1,
                    numberOfExercise = numberOfExercise,
                    trainingId = trainingId,
                    navigator = navigator,
                    seriesViewModel = seriesViewModel,
                    trainingName = trainingName
                )
            }
    }
}