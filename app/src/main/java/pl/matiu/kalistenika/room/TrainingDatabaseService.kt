package pl.matiu.kalistenika.room

import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.viewModel.TrainingViewModel

class TrainingDatabaseService {

    val trainingDao = MainApplication.exerciseDatabase.getTrainingDao()

    fun getAllTraining(): List<TrainingModel> {
        return trainingDao.getAllTrainings()
    }

    fun addTraining(trainingModel: TrainingModel) {
        trainingDao.addTraining(trainingModel)
    }

    fun deleteTraining(trainingModel: TrainingModel) {
        ExerciseDatabaseService().deleteAllExerciseByTrainingId(trainingModel.trainingId)
        trainingDao.deleteTraining(trainingModel.trainingId)
    }
}