package pl.matiu.kalistenika.exerciseApi

import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NinjaApi {

    @GET("exercises")
    @Headers("x-api-key: T4ORxVmT0po4+uRIvCN9VA==eyUBgAZmjaRpJyml")
    suspend fun getMuscleExercise(@Query("muscle") muscle: String = "chest"): List<ExerciseApi>
}