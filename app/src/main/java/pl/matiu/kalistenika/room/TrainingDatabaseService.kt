package pl.matiu.kalistenika.room

import pl.matiu.kalistenika.MainApplication
import pl.matiu.kalistenika.model.training.TrainingModel

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