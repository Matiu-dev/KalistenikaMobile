package pl.matiu.kalistenika.composable.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route: NavKey {
    @Serializable
    data object TrainingScreen: NavKey, Route
    @Serializable
    data class SeriesScreen(val trainingName: String, val trainingId: String): NavKey, Route
    @Serializable
    data class CreateSeries(val trainingId: Int, val trainingName: String): NavKey, Route
    @Serializable
    data class EditRepetitionSeries(val trainingId: Int, val exerciseId: Int): NavKey, Route
    @Serializable
    data class EditTimeSeries(val trainingId: Int, val exerciseId: Int): NavKey, Route
    @Serializable
    data object ChangeLanguage: NavKey, Route
    @Serializable
    data object HistoryScreen: NavKey, Route
    @Serializable
    data class HistoryDetailsScreen(val date: String): NavKey, Route
}