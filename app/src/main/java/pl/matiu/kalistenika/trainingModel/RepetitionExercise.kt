package pl.matiu.kalistenika.trainingModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RepetitionExercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int = 0,
    val exerciseName: String,
    val numberOfSeries: Int,
    val numberOfReps: Int,
    val stopTimer: Boolean,
    val breakBetweenSeries: Int,
    override var positionInTraining: Int?,
    override val trainingId: Int?
) : SeriesInterface