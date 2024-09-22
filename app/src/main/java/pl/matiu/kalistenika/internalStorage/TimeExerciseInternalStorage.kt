package pl.matiu.kalistenika.internalStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.matiu.kalistenika.trainingModel.RepetitionExercise
import pl.matiu.kalistenika.trainingModel.TimeExercise
import java.io.File

class TimeExerciseInternalStorage {

    //TODO to odestowania
    private val TIME_EXERCISE_FILE_NAME: String = "TimeExerciseList"
    private val REPETITION_EXERCISE_FILE_NAME: String = "RepetitionExerciseList"

    //TODO mozna sprobowac tworzyc nowy plik cwiczen dla kazdego cwiczenia i potem usuwac przy usunieciu treningu
    //TODO nazwa pliku to np nazwa treningu + time lub rep
    fun saveTimeExerciseToInternalStorage(
        context: Context,
        exercise: TimeExercise,
        trainingName: String
    ) {
        val file = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        // Ładowanie istniejących ćwiczeń czasowych i powtórzeniowych
        val timeExercisesList = loadTimeExerciseFromInternalStorage(context, trainingName)
        val repetitionExercisesList = RepetitionExerciseInternalStorage()
            .loadRepetitionExerciseFromInternalStorage(context, TrainingInternalStorageService().getTrainingNameById(context, exercise.trainingId))

        // Łączenie i sortowanie list
        val exerciseList = (timeExercisesList + repetitionExercisesList)
            .sortedBy { it.positionInTraining }
            .toMutableList()

        // Aktualizacja pozycji elementów, aby zrobić miejsce dla nowego ćwiczenia
        for (i in exerciseList.indices) {
            if (exerciseList[i].positionInTraining!! >= exercise.positionInTraining!!) {
                exerciseList[i].positionInTraining = exerciseList[i].positionInTraining!! + 1
            }
        }

        // Dodanie nowego ćwiczenia z odpowiednim exerciseId
        val newExercise = if (timeExercisesList.isEmpty()) {
            // Jeśli lista czasowa jest pusta, ustaw exerciseId na 0
            exercise.copy(exerciseId = 0)
        } else {
            // W przeciwnym razie, ustaw exerciseId na ostatni id + 1
            exercise.copy(exerciseId = timeExercisesList.maxOf { it.exerciseId } + 1)
        }

        // Dodanie nowego ćwiczenia na odpowiednią pozycję
        exerciseList.add(newExercise)

        // Sortowanie listy po dodaniu nowego ćwiczenia
        exerciseList.sortBy { it.positionInTraining }

        // Aktualizacja listy czasowej
        val updatedTimeExercisesList = exerciseList.filterIsInstance<TimeExercise>()

        // Zapis zaktualizowanej listy do pliku
        file.writeText(gson.toJson(updatedTimeExercisesList))
    }


    fun loadTimeExerciseFromInternalStorage(context: Context, trainingName: String): MutableList<TimeExercise> {
        val file = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<TimeExercise>>() {}.type

        return if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }



    //TODO - poprawic
    fun updateTimeExerciseToInternalStorage(
        context: Context,
        exerciseId: Int,
        timeExercise: TimeExercise,
        trainingName: String
    ) {
        val timeFile = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val repetitionFile = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        var timeExercisesList = loadTimeExerciseFromInternalStorage(context, trainingName)
        var repetitionExercisesList = RepetitionExerciseInternalStorage().loadRepetitionExerciseFromInternalStorage(context,
            TrainingInternalStorageService().getTrainingNameById(context, timeExercise.trainingId))

        // Usunięcie elementu z listy czasowych
        val removedTimeExercise = timeExercisesList.find { it.exerciseId == exerciseId }
        if (removedTimeExercise != null) {
            timeExercisesList.remove(removedTimeExercise)
        }

        // Naprawa pozycji po usunięciu elementu
        if (removedTimeExercise != null) {
            for (exercise in (timeExercisesList + repetitionExercisesList)) {
                if (exercise.positionInTraining!! > removedTimeExercise.positionInTraining!!) {
                    exercise.positionInTraining = exercise.positionInTraining!! - 1
                }
            }
        }

        // Aktualizacja pozycji w nowej liście
        for (exercise in (timeExercisesList + repetitionExercisesList)) {
            if (exercise.positionInTraining!! >= timeExercise.positionInTraining!!) {
                exercise.positionInTraining = exercise.positionInTraining!! + 1
            }
        }

        // Dodanie aktualizowanego ćwiczenia do listy
        timeExercisesList.add(timeExercise)

        // Sortowanie listy na podstawie pozycji
        timeExercisesList.sortBy { it.positionInTraining }
        repetitionExercisesList.sortBy { it.positionInTraining }

        // Aktualizacja danych
        timeFile.writeText(gson.toJson(timeExercisesList))
        repetitionFile.writeText(gson.toJson(repetitionExercisesList))
    }

    fun deleteTimeExerciseById(context: Context, exerciseId: Int, trainingId: Int?, trainingName: String) {
        val timeFile = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val repetitionFile = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        var timeExercisesList = loadTimeExerciseFromInternalStorage(context, trainingName)
        var repetitionExercisesList = RepetitionExerciseInternalStorage().loadRepetitionExerciseFromInternalStorage(context,
            TrainingInternalStorageService().getTrainingNameById(context, trainingId))

        // Usunięcie elementu z listy czasowych
        val removedTimeExercise = timeExercisesList.find { it.exerciseId == exerciseId }
        if (removedTimeExercise != null) {
            timeExercisesList.remove(removedTimeExercise)
        }

        // Naprawa pozycji po usunięciu elementu
        if (removedTimeExercise != null) {
            for (exercise in (timeExercisesList + repetitionExercisesList)) {
                if (exercise.positionInTraining!! > removedTimeExercise.positionInTraining!!) {
                    exercise.positionInTraining = exercise.positionInTraining!! - 1
                }
            }
        }

        // Aktualizacja danych
        timeFile.writeText(gson.toJson(timeExercisesList))
        repetitionFile.writeText(gson.toJson(repetitionExercisesList))
    }

    fun getNumberOfExerciseForTrainingId(context: Context, trainingId: Int, trainingName: String): Int {

        var timeExerciseList: MutableList<TimeExercise> = loadTimeExerciseFromInternalStorage(context = context, trainingName)

        var repetitionExerciseList: MutableList<RepetitionExercise> =
            RepetitionExerciseInternalStorage().loadRepetitionExerciseFromInternalStorage(context, trainingName = trainingName)

        return timeExerciseList.filter { it.trainingId == trainingId }.size + repetitionExerciseList.filter { it.trainingId == trainingId }.size
    }

    fun findTimeExerciseById(context: Context, exerciseId: Int, trainingName: String): TimeExercise? {
        return loadTimeExerciseFromInternalStorage(context = context, trainingName).find { it.exerciseId == exerciseId }
    }
}