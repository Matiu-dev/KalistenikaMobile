package pl.matiu.kalistenika.realtimeDatabase

import android.annotation.SuppressLint
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.Firebase
import com.google.firebase.database.database
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.Logger
import pl.matiu.kalistenika.model.history.HistoryModel
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.viewModel.NinjaApiViewModel
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RealTimeDatabaseService {

    private val database = Firebase.database
    private val logger: Logger = ConsoleLogger()

    @SuppressLint("SimpleDateFormat")
    private val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    fun saveExerciseToRealtimeDatabase(exerciseList: List<ExerciseApi>?) {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                exerciseList?.forEach { exercise ->
                    val ref = database.getReference("/Exercises/${exercise.name}")
                    ref.setValue(exercise)
                }
            } catch (e: Exception) {
                logger.log("failed downloading exercises", "$e")
            }
        }
    }

    fun loadData(onDataLoaded: (List<ExerciseApi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ref = database.getReference("/Exercises")
                ref.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val exerciseList = snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.getValue(ExerciseApi::class.java)
                        }
                        onDataLoaded(exerciseList)
                    } else {
                        logger.log("load data", "Data not found")
                    }
                }.addOnFailureListener { e ->
                    logger.log("load data", "failed to load data: $e")
                }
            } catch (e: Exception) {
                logger.log("load data", "failed downloading exercises $e")
            }
        }
    }

    fun writeDataHistory(exercise: SeriesInterface) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                when (exercise) {
                    is RepetitionExercise -> {
                        database.getReference("/History/${myDateFormat.format(Date())}/${exercise.repetitionExerciseName}")
                            .setValue(HistoryModel(exercise))
                    }

                    is TimeExercise -> {
                        database.getReference("/History/${myDateFormat.format(Date())}/${exercise.timeExerciseName}")
                            .setValue(HistoryModel(exercise))
                    }
                }
            } catch (e: Exception) {
                logger.log("failed saving exercise to history", "$e")
            }
        }
    }

    fun loadDataHistoryRepetitionExercise(date: String, onDataLoaded: (List<RepetitionExercise>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ref = database.getReference("/History/${date}")
                ref.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val repetitionExerciseList = snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.child("exercise").getValue(RepetitionExercise::class.java)
                        }
                        onDataLoaded(repetitionExerciseList)
                    } else {
                        logger.log("load data", "Data not found")
                    }
                }.addOnFailureListener { e ->
                    logger.log("load data", "failed to load data: $e")
                }
            } catch (e: Exception) {
                logger.log("load data", "failed downloading exercises $e")
            }
        }
    }

    fun loadDataHistoryTimeExercise(date: String, onDataLoaded: (List<TimeExercise>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ref = database.getReference("/History/${date}")
                ref.get().addOnSuccessListener { snapshot ->
                    if (snapshot.exists()) {
                        val timeExerciseList = snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.child("exercise").getValue(TimeExercise::class.java)
                        }
                        onDataLoaded(timeExerciseList)
                    } else {
                        logger.log("load data", "Data not found")
                    }
                }.addOnFailureListener { e ->
                    logger.log("load data", "failed to load data: $e")
                }
            } catch (e: Exception) {
                logger.log("load data", "failed downloading exercises $e")
            }
        }
    }
}