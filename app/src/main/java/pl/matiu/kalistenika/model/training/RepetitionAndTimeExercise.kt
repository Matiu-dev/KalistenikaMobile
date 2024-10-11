package pl.matiu.kalistenika.model.training

import androidx.room.Embedded

data class RepetitionAndTimeExercise (
    @Embedded val repetitionExercise: RepetitionExercise,
    @Embedded val timeExercise: TimeExercise
)