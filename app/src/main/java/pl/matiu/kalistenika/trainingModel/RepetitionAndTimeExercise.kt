package pl.matiu.kalistenika.trainingModel

import androidx.room.Embedded

data class RepetitionAndTimeExercise (
    @Embedded val repetitionExercise: RepetitionExercise,
    @Embedded val timeExercise: TimeExercise
)