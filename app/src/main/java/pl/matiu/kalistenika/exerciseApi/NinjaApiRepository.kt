package pl.matiu.kalistenika.exerciseApi

interface NinjaApiRepository {
    suspend fun getExercises()
}