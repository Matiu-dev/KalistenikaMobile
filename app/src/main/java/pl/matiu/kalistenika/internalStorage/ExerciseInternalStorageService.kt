package pl.matiu.kalistenika.internalStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory
import pl.matiu.kalistenika.training.model.RepetitionExercise
import pl.matiu.kalistenika.training.model.SeriesInterface
import pl.matiu.kalistenika.training.model.TimeExercise
import pl.matiu.kalistenika.training.model.TrainingModel
import java.io.File
import java.sql.Time

class ExerciseInternalStorageService {

    private val TIME_EXERCISE_FILE_NAME: String = "TimeExerciseList"
    private val REPETITION_EXERCISE_FILE_NAME: String = "RepetitionExerciseList"

    fun saveRepetitionExerciseToInternalStorage(
        context: Context,
        repetitionExercise: RepetitionExercise
    ) {

        var file = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<RepetitionExercise>>() {}.type

        var trainingList: MutableList<RepetitionExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.add(

            if (trainingList.isEmpty()) {
                RepetitionExercise(
                    0,
                    repetitionExercise.exerciseName,
                    repetitionExercise.numberOfSeries,
                    repetitionExercise.numberOfReps,
                    repetitionExercise.stopTimer,
                    repetitionExercise.breakBetweenSeries,
                    repetitionExercise.trainingId
                )
            } else {
                RepetitionExercise(
                    trainingList[trainingList.size - 1].exerciseId + 1,
                    repetitionExercise.exerciseName,
                    repetitionExercise.numberOfSeries,
                    repetitionExercise.numberOfReps,
                    repetitionExercise.stopTimer,
                    repetitionExercise.breakBetweenSeries,
                    repetitionExercise.trainingId
                )
            }

        )

        file.writeText(gson.toJson(trainingList))
    }

    fun saveTimeExerciseToInternalStorage(
        context: Context,
        timeExercise: TimeExercise
    ) {

        var file = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<TimeExercise>>() {}.type

        var trainingList: MutableList<TimeExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.add(

            if (trainingList.isEmpty()) {
                TimeExercise(
                    0,
                    timeExercise.exerciseName,
                    timeExercise.numberOfSeries,
                    timeExercise.timeForSeries,
                    timeExercise.breakBetweenSeries,
                    timeExercise.trainingId
                )
            } else {
                TimeExercise(
                    trainingList[trainingList.size - 1].exerciseId + 1,
                    timeExercise.exerciseName,
                    timeExercise.numberOfSeries,
                    timeExercise.timeForSeries,
                    timeExercise.breakBetweenSeries,
                    timeExercise.trainingId
                )
            }

        )

        file.writeText(gson.toJson(trainingList))
    }

    fun loadTimeExerciseToInternalStorage(context: Context): List<TimeExercise> {

        var file: File = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<TimeExercise>>() {}.type

        return if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun findTimeExerciseById(context: Context, exerciseId: Int): TimeExercise? {
        return loadTimeExerciseToInternalStorage(context = context).find { it.exerciseId == exerciseId }
    }

    fun loadRepetitionExerciseToInternalStorage(context: Context): List<RepetitionExercise> {

        var file: File = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<RepetitionExercise>>() {}.type

        return if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: emptyList()
        } else {
            emptyList()
        }
    }

    fun updateRepetitionExerciseToInternalStorage(context: Context, exerciseId: Int, repetitionExercise: RepetitionExercise) {
        var file = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<RepetitionExercise>>() {}.type

        var trainingList: MutableList<RepetitionExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.removeIf { it.exerciseId == exerciseId }
        trainingList.add(repetitionExercise)

        file.writeText(gson.toJson(trainingList))
    }

    fun updateTimeExerciseToInternalStorage(context: Context, exerciseId: Int, timeExercise: TimeExercise) {
        var file = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<TimeExercise>>() {}.type

        var trainingList: MutableList<TimeExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.removeIf { it.exerciseId == exerciseId }
        trainingList.add(timeExercise)

        file.writeText(gson.toJson(trainingList))
    }

    fun findRepetitionExerciseById(context: Context, exerciseId: Int): RepetitionExercise? {
        return loadRepetitionExerciseToInternalStorage(context = context).find { it.exerciseId == exerciseId }
    }

    fun deleteRepetitionExerciseById(context: Context, exerciseId: Int) {
        var file = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<RepetitionExercise>>() {}.type

        var trainingList: MutableList<RepetitionExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.removeIf { it.exerciseId == exerciseId }

        file.writeText(gson.toJson(trainingList))
    }

    fun deleteTimeExerciseById(context: Context, exerciseId: Int) {
        var file = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<TimeExercise>>() {}.type

        var trainingList: MutableList<TimeExercise> = if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }

        trainingList.removeIf { it.exerciseId == exerciseId }

        file.writeText(gson.toJson(trainingList))
    }

//    fun deleteExerciseFromInternalStorage(context: Context, trainingId: Int) {
//        var file = File(
//            context.getExternalFilesDir(null),
//            EXERCISES_FILE_NAME
//        )
//
//        val gson = Gson()
//        val itemType = object : TypeToken<List<TrainingModel>>() {}.type
//
//        var trainingList: MutableList<TrainingModel> = if (file.exists()) {
//            val fileContent = file.readText()
//            gson.fromJson(fileContent, itemType) ?: mutableListOf()
//        } else {
//            mutableListOf()
//        }
//
//        trainingList.removeAt(trainingId)
//
//        file.writeText(gson.toJson(trainingList))
//    }
}