package pl.matiu.kalistenika.ui.dialog

import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise

sealed class Dialog {
    object CreateRepetitionSeries: Dialog()
    object CreateTimeSeries: Dialog()
    object UpdateRepetitionSeries: Dialog()
    object UpdateTimeSeries: Dialog()
    object DeleteRepetitionSeries: Dialog()
    object DeleteTimeSeries: Dialog()
}
