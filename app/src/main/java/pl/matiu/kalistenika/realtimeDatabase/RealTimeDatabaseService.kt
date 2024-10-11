package pl.matiu.kalistenika.realtimeDatabase

import android.annotation.SuppressLint
import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.exerciseApi.NinjaApiService
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.Logger
import pl.matiu.kalistenika.model.history.HistoryModel
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class RealTimeDatabaseService {

    private val database = Firebase.database
    private val logger: Logger = ConsoleLogger()

    @SuppressLint("SimpleDateFormat")
    private val myDateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
    fun writeData() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exerciseList = NinjaApiService().getExercises()

                exerciseList.forEach { exercise ->

                    val ref = database.getReference("/Exercises/${exercise.name}")
                    ref.setValue(exercise)

//                    logger.log("$exercise")
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

    fun loadDataHistory(onDataLoaded: (List<ExerciseApi>) -> Unit) {
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
}