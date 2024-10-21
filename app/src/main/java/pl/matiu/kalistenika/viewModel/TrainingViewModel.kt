package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.room.ExerciseDatabaseService

class TrainingViewModel : ViewModel() {

    private var _trainingList = MutableStateFlow<List<TrainingModel>?>(null)
    val trainingList = _trainingList.asStateFlow()

    init {
        getAllTraining()
    }

    fun getAllTraining() {
        viewModelScope.launch {
            ThreadIdLogger(ConsoleLogger()).log("training view model", "data downloading")
            _trainingList.value = withContext(Dispatchers.IO) {
                TrainingDatabaseService().getAllTraining()
            }
        }

    }

    fun addTraining(trainingModel: TrainingModel) {
        ConsoleLogger().log("training view model", "adding new training ${trainingModel.name}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                TrainingDatabaseService().addTraining(trainingModel)
                TrainingDatabaseService().getAllTraining()
            }
        }
    }

    fun deleteTraining(trainingModel: TrainingModel) {
        ConsoleLogger().log("training view model", "deleting training ${trainingModel.name}")
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                TrainingDatabaseService().deleteTraining(trainingModel)
                TrainingDatabaseService().getAllTraining()
            }
        }
    }
}