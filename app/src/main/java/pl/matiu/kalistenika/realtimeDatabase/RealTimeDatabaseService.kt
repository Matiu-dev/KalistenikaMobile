package pl.matiu.kalistenika.realtimeDatabase

import com.google.firebase.Firebase
import com.google.firebase.database.database
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.exerciseApi.NinjaApiService
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.Logger
import java.lang.Exception

class RealTimeDatabaseService {

    private val database = Firebase.database
    private val logger: Logger = ConsoleLogger()
    fun writeData() {

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val exerciseList = NinjaApiService().getExercises()

                exerciseList.forEach { exercise ->

                    val ref = database.getReference("/Exercises/${exercise.name}")
                    ref.setValue(exercise)

                    logger.log("$exercise")
                }
            } catch (e: Exception) {
                logger.log("failed downloading exercises $e")
            }

        }
    }

    fun loadData(onDataLoaded: (List<ExerciseApi>) -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val ref = database.getReference("/Exercises")
                ref.get().addOnSuccessListener { snapshot ->
                    if(snapshot.exists()) {
                        val exerciseList = snapshot.children.mapNotNull { dataSnapshot ->
                            dataSnapshot.getValue(ExerciseApi::class.java)
                        }
                        onDataLoaded(exerciseList)
                    } else {
                        logger.log("Data not found")
                    }
                }.addOnFailureListener { e ->
                    logger.log("failed to load data: $e")
                }
            } catch (e: Exception) {
                logger.log("failed downloading exercises $e")
            }
        }
    }
}