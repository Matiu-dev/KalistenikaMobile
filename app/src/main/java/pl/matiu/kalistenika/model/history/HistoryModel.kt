package pl.matiu.kalistenika.model.history

import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise
import java.util.Date

data class HistoryModel(
    val exercise: SeriesInterface? = null
)