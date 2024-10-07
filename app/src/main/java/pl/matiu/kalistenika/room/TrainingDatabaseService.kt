package pl.matiu.kalistenika.room

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.trainingModel.TrainingModel

class TrainingDatabaseService {

    val trainingDao = MainApplication.exerciseDatabase.getTrainingDao()

    fun getAllTraining(): List<TrainingModel> {
        return trainingDao.getAllTrainings()
    }

    fun addTraining(name: String) {
        trainingDao.addTraining(TrainingModel(name = name))
    }

    fun deleteTraining(trainingId: Int) {
        ExerciseDatabaseService().deleteAllExerciseByTrainingId(trainingId)
        trainingDao.deleteTraining(trainingId)
    }
}