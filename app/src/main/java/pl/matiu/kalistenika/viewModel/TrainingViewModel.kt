package pl.matiu.kalistenika.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.model.training.TrainingModel
import javax.inject.Inject

@HiltViewModel
class TrainingViewModel @Inject constructor(private val trainingDatabaseService: TrainingDatabaseService) : ViewModel() {

    private var _trainingList = MutableStateFlow<List<TrainingModel>?>(null)
    val trainingList = _trainingList.asStateFlow()

    private val _isTrainingLoading = MutableStateFlow(false)
    val isTrainingLoading = _isTrainingLoading.asStateFlow()

    init {
        getAllTraining()
    }

    private fun getAllTraining() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ThreadIdLogger(ConsoleLogger()).log("training view model", "data downloading")
                _isTrainingLoading.value = true
                _trainingList.value = trainingDatabaseService.getAllTraining()
                _isTrainingLoading.value = false
            }
        }

    }

    fun addTraining(trainingModel: TrainingModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ThreadIdLogger(ConsoleLogger()).log("training view model", "adding new training ${trainingModel.name}")
                _isTrainingLoading.value = true
                trainingDatabaseService.addTraining(trainingModel)
                trainingDatabaseService.getAllTraining()
                _isTrainingLoading.value = false
            }
        }
    }

    fun deleteTraining(trainingModel: TrainingModel) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                ThreadIdLogger(ConsoleLogger()).log("training view model", "deleting training ${trainingModel.name}")
                _isTrainingLoading.value = true
                trainingDatabaseService.deleteTraining(trainingModel)
                trainingDatabaseService.getAllTraining()
                _isTrainingLoading.value = false
            }
        }
    }
}