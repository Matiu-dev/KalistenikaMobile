package pl.matiu.kalistenika.myViewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.internalStorage.LoadDataFromLocalMemory
import pl.matiu.kalistenika.trainingModel.SeriesInterface

class SeriesViewModel: ViewModel() {

    private val loadDataFromLocalMemory = LoadDataFromLocalMemory()

    private var _exerciseList = MutableStateFlow<List<SeriesInterface>?>(null)
    val exerciseList = _exerciseList.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    suspend fun loadData(context: Context, trainingId: Int?) {
        if (_exerciseList.value == null) {
            viewModelScope.launch {
                _isLoading.value = true

                _exerciseList.value = loadDataFromLocalMemory.loadDataFromLocalMemory(context, trainingId)

                _isLoading.value = false
            }
        }
    }
}

