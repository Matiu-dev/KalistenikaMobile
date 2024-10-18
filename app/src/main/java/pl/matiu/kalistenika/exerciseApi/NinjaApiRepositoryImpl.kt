package pl.matiu.kalistenika.exerciseApi

import android.util.Log
import java.util.logging.Logger

class NinjaApiRepositoryImpl(private val api: NinjaApi): NinjaApiRepository {
    override suspend fun getExercises() {
        api.getMuscleExercise()
    }
}