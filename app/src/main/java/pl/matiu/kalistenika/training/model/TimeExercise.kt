package pl.matiu.kalistenika.training.model

import pl.matiu.kalistenika.training.model.SeriesInterface

data class TimeExercise(val exerciseId: Int, val exerciseName: String, val numberOfSeries: Int,
                   val timeForSeries: Int, val breakBetweenSeries: Int,
    override val trainingId: Int?) : SeriesInterface