package pl.matiu.kalistenika.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.exerciseApi.ExerciseApi
import pl.matiu.kalistenika.exerciseApi.NinjaApiRepository
import pl.matiu.kalistenika.model.training.SeriesInterface
import javax.inject.Inject

@HiltViewModel
class NinjaApiViewModel @Inject constructor(private val ninjaApiRepository: NinjaApiRepository): ViewModel() {

    private var _ninjaApiExerciseList = MutableStateFlow<List<ExerciseApi>?>(null)
    val ninjaApiExerciseList = _ninjaApiExerciseList.asStateFlow()

    fun getExercises() {
        viewModelScope.launch {
            Log.d("data downloading", "downloading data from ninja api")
            ninjaApiRepository.getExercises()
        }
    }
}