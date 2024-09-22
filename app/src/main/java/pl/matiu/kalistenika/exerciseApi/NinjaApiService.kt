package pl.matiu.kalistenika.exerciseApi

import android.util.Log
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NinjaApiService {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.api-ninjas.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val service: NinjaApi by lazy {
        retrofit.create(NinjaApi::class.java)
    }
    suspend fun getExercises(): List<ExerciseApi>  {
        return service.getMuscleExercise()
    }
}