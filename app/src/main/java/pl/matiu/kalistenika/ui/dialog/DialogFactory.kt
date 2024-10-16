package pl.matiu.kalistenika.ui.dialog

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import pl.matiu.kalistenika.model.training.SeriesInterface

class DialogFactory {

    fun createDialog(dialogType: DialogType): Dialog {
        return when(dialogType) {
            DialogType.CREATE_TIME_SERIES -> Dialog.CreateTimeSeries
            DialogType.CREATE_REPETITION_SERIES -> Dialog.CreateRepetitionSeries
            DialogType.UPDATE_TIME_SERIES -> Dialog.UpdateTimeSeries
            DialogType.UPDATE_REPETITION_SERIES -> Dialog.UpdateRepetitionSeries
            DialogType.DELETE_TIME_SERIES -> Dialog.DeleteTimeSeries
            DialogType.DELETE_REPETITION_SERIES -> Dialog.DeleteRepetitionSeries
        }
    }

    @Composable
    fun ShowDialog(dialog: Dialog) {
        when(dialog) {
            is Dialog.CreateTimeSeries -> CreateTimeSeries()
            is Dialog.CreateRepetitionSeries -> CreateRepetitionSeries()

            is Dialog.UpdateTimeSeries -> UpdateTimeSeries()
            is Dialog.UpdateRepetitionSeries -> UpdateRepetitionSeries()

            is Dialog.DeleteTimeSeries -> DeleteTimeSeries()
            is Dialog.DeleteRepetitionSeries -> DeleteRepetitionSeries()

        }
    }

    @Composable
    fun CreateTimeSeries() {
        Text(text = "create time series")
    }

    @Composable
    fun CreateRepetitionSeries() {
        Text(text = "create repetition series")
    }

    @Composable
    fun UpdateTimeSeries() {
        Text(text = "update time series")
    }

    @Composable
    fun UpdateRepetitionSeries() {
        Text(text = "update repetition series")
    }

    @Composable
    fun DeleteTimeSeries() {
        Text(text = "delete time series")
    }

    @Composable
    fun DeleteRepetitionSeries() {
        Text(text = "delete repetition series")
    }
}