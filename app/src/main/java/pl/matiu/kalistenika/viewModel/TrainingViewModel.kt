package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.trainingModel.TrainingModel

class TrainingViewModel: ViewModel() {

    val trainingDao = MainApplication.trainingDatabase.getTrainingDao()

    private var _trainingList = MutableStateFlow<List<TrainingModel>?>(null)
    val trainingList = _trainingList.asStateFlow()


    init {
        getAllTraining()
    }

    fun getAllTraining() {
        _trainingList.value = trainingDao.getAllTrainings()
    }

    fun addTraining(name: String) {
        viewModelScope.launch {
            trainingDao.addTraining(TrainingModel(name = name))
        }

    }

    fun deleteTraining(trainingId: Int) {
        viewModelScope.launch {

            SeriesViewModel().deleteAllExerciseByTrainingId(trainingId)

            trainingDao.deleteTraining(trainingId)
        }
    }
}