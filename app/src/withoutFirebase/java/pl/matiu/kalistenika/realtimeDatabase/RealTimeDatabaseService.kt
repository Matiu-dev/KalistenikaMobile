package pl.matiu.kalistenika.realtimeDatabase

import android.annotation.SuppressLint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import java.text.SimpleDateFormat
import java.util.Locale

class RealTimeDatabaseService {

//    private val database = Firebase.database
//    private val logger: Logger = ConsoleLogger()

    @SuppressLint("SimpleDateFormat")
    private val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)

    fun saveExerciseToRealtimeDatabase(exerciseList: List<ExerciseApi>?) {

    }

    fun loadData(onDataLoaded: (List<ExerciseApi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

    fun writeDataHistory(exercise: SeriesInterface) {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

    fun loadDataHistoryRepetitionExercise(date: String, onDataLoaded: (List<RepetitionExercise>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }

    fun loadDataHistoryTimeExercise(date: String, onDataLoaded: (List<TimeExercise>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {

        }
    }
}