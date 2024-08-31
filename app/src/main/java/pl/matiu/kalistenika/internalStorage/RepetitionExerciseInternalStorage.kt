package pl.matiu.kalistenika.internalStorage

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import pl.matiu.kalistenika.training.model.RepetitionExercise
import java.io.File

class RepetitionExerciseInternalStorage {

    private val REPETITION_EXERCISE_FILE_NAME: String = "RepetitionExerciseList"
    private val TIME_EXERCISE_FILE_NAME: String = "TimeExerciseList"
//TODO mozna sprobowac tworzyc nowy plik cwiczen dla kazdego cwiczenia i potem usuwac przy usunieciu treningu
    //TODO nazwa pliku to np nazwa treningu + time lub rep
    fun saveRepetitionExerciseToInternalStorage(
        context: Context,
        exercise: RepetitionExercise,//TODO sprawdzic czy jak tutaj dam training id i dostosuje do TimeExerciseInternalStorage().loadTimeExerciseFromInternalStorage(context) to czy to starczy aby prawidlo
        //wo sie aktualizowaly dane
        trainingName: String
    ) {

        var file = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        val timeExercisesList = TimeExerciseInternalStorage().loadTimeExerciseFromInternalStorage(context, trainingName)
        val repetitionExercisesList = loadRepetitionExerciseFromInternalStorage(context, trainingName)

        var exerciseList =
            (timeExercisesList + repetitionExercisesList).sortedBy { it.positionInTraining }
                .toMutableList()

        //

        // Aktualizacja pozycji elementów, aby zrobić miejsce dla nowego ćwiczenia
        for (i in exerciseList.indices) {
            if (exerciseList[i].positionInTraining!! >= exercise.positionInTraining!!) {
                exerciseList[i].positionInTraining = exerciseList[i].positionInTraining!! + 1
            }
        }

        // Dodanie nowego ćwiczenia z odpowiednim exerciseId
        val newExercise = if (repetitionExercisesList.isEmpty()) {
            // Jeśli lista powtórzeniowa jest pusta, ustaw exerciseId na 0
            exercise.copy(exerciseId = 0)
        } else {
            // W przeciwnym razie, ustaw exerciseId na ostatni id + 1
            exercise.copy(exerciseId = repetitionExercisesList.maxOf { it.exerciseId } + 1)
        }

        // Dodanie nowego ćwiczenia na odpowiednią pozycję
        exerciseList.add(newExercise)

        // Sortowanie listy po dodaniu nowego ćwiczenia
        exerciseList.sortBy { it.positionInTraining }

        // Aktualizacja listy powtórzeniowej
        val updatedRepetitionExercisesList = exerciseList.filterIsInstance<RepetitionExercise>()

        // Zapis zaktualizowanej listy do pliku
        file.writeText(gson.toJson(updatedRepetitionExercisesList))
    }

    fun loadRepetitionExerciseFromInternalStorage(context: Context, trainingName: String): MutableList<RepetitionExercise> {
        var file = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()
        val itemType = object : TypeToken<List<RepetitionExercise>>() {}.type

        return if (file.exists()) {
            val fileContent = file.readText()
            gson.fromJson(fileContent, itemType) ?: mutableListOf()
        } else {
            mutableListOf()
        }
    }

    fun updateRepetitionExerciseToInternalStorage(
        context: Context,
        exerciseId: Int,
        repetitionExercise: RepetitionExercise,
        trainingName: String
    ) {

        var repetitionFile = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        var timeFile = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        var timeExercisesList = TimeExerciseInternalStorage().loadTimeExerciseFromInternalStorage(context, trainingName)
        var repetitionExercisesList = loadRepetitionExerciseFromInternalStorage(context, trainingName)

        // Usunięcie elementu z listy powtórzeń
        val removedRepetitionExercise = repetitionExercisesList.find { it.exerciseId == exerciseId }
        if (removedRepetitionExercise != null) {
            repetitionExercisesList.remove(removedRepetitionExercise)
        }

        // Naprawa pozycji po usunięciu elementu
        if (removedRepetitionExercise != null) {
            for (exercise in (timeExercisesList + repetitionExercisesList)) {
                if (exercise.positionInTraining!! > removedRepetitionExercise.positionInTraining!!) {
                    exercise.positionInTraining = exercise.positionInTraining!! - 1
                }
            }
        }

        // Aktualizacja pozycji w nowej liście
        for (exercise in (timeExercisesList + repetitionExercisesList)) {
            if (exercise.positionInTraining!! >= repetitionExercise.positionInTraining!!) {
                exercise.positionInTraining = exercise.positionInTraining!! + 1
            }
        }

        // Dodanie aktualizowanego ćwiczenia do listy
        repetitionExercisesList.add(repetitionExercise)

        // Sortowanie listy na podstawie pozycji
        repetitionExercisesList.sortBy { it.positionInTraining }
        timeExercisesList.sortBy { it.positionInTraining }

        // Aktualizacja danych
        repetitionFile.writeText(gson.toJson(repetitionExercisesList))
        timeFile.writeText(gson.toJson(timeExercisesList))
    }

    fun deleteRepetitionExerciseById(context: Context, exerciseId: Int, trainingName: String) {
        var repetitionFile = File(
            context.getExternalFilesDir(null),
            REPETITION_EXERCISE_FILE_NAME + trainingName
        )

        var timeFile = File(
            context.getExternalFilesDir(null),
            TIME_EXERCISE_FILE_NAME + trainingName
        )

        val gson = Gson()

        var timeExercisesList = TimeExerciseInternalStorage().loadTimeExerciseFromInternalStorage(context, trainingName)
        var repetitionExercisesList = loadRepetitionExerciseFromInternalStorage(context, trainingName)

        // Usunięcie elementu z listy powtórzeń
        val removedRepetitionExercise = repetitionExercisesList.find { it.exerciseId == exerciseId }
        if (removedRepetitionExercise != null) {
            repetitionExercisesList.remove(removedRepetitionExercise)
        }

        // Naprawa pozycji po usunięciu elementu
        if (removedRepetitionExercise != null) {
            for (exercise in (timeExercisesList + repetitionExercisesList)) {
                if (exercise.positionInTraining!! > removedRepetitionExercise.positionInTraining!!) {
                    exercise.positionInTraining = exercise.positionInTraining!! - 1
                }
            }
        }

        // Aktualizacja danych
        repetitionFile.writeText(gson.toJson(repetitionExercisesList))
        timeFile.writeText(gson.toJson(timeExercisesList))
    }

    fun findRepetitionExerciseById(context: Context, exerciseId: Int, trainingName: String): RepetitionExercise? {
        return loadRepetitionExerciseFromInternalStorage(context = context, trainingName).find { it.exerciseId == exerciseId }
    }
}