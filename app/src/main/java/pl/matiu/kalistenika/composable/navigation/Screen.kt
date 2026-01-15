package pl.matiu.kalistenika.composable.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

data object Home
data object CreateTraining
data class CreateSeries(val trainingId: Int, val trainingName: String)
data class EditRepetitionSeries(val trainingId: Int, val exerciseId: Int)
data class EditTimeSeries(val trainingId: Int, val exerciseId: Int)
data object ChangeLanguage
data object HistoryScreen
data class HistoryDetailsScreen(val date: String)
data class SeriesScreen(val trainingName: String, val trainingId: Int)