package pl.matiu.kalistenika.room

import androidx.room.Transaction
import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import javax.inject.Inject

class TrainingDatabaseService @Inject constructor(private val trainingDao: TrainingDao,
    private val exerciseDao: ExerciseDao) {

    fun getAllTraining(): List<TrainingModel> {
        return trainingDao.getAllTrainings()
    }

    fun addTraining(trainingModel: TrainingModel) {
        trainingDao.addTraining(trainingModel)
    }

    @Transaction
    fun deleteTraining(trainingModel: TrainingModel) {
        exerciseDao.deleteTimeExerciseByTrainingId(trainingModel.trainingId)
        exerciseDao.deleteRepetitionExerciseByTrainingId(trainingModel.trainingId)
        trainingDao.deleteTraining(trainingModel.trainingId)
    }
}