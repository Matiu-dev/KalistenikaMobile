package pl.matiu.kalistenika.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.Logger
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.trainingModel.TrainingModel

class TrainingViewModel: ViewModel() {

    private var _trainingList = MutableStateFlow<List<TrainingModel>?>(null)
    val trainingList = _trainingList.asStateFlow()

    init {
        getAllTraining()
    }

    fun getAllTraining() {
        ConsoleLogger().log("data downloading", "trainingviewmodel")
        viewModelScope.launch {
            _trainingList.value = TrainingDatabaseService().getAllTraining()
        }

    }
}