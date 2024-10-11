package pl.matiu.kalistenika.model.training

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(    foreignKeys = [ForeignKey(
    entity = TrainingModel::class,
    parentColumns = ["trainingId"],
    childColumns = ["repetitionTrainingId"],
    onDelete = ForeignKey.CASCADE
)],
    indices = [Index("repetitionTrainingId")]
)
data class RepetitionExercise(
    @PrimaryKey(autoGenerate = true)
    val repetitionExerciseId: Int = 0,
    val repetitionExerciseName: String,
    val numberOfRepetitionSeries: Int,
    val numberOfReps: Int,
    val stopTimer: Boolean,
    val breakBetweenRepetitionSeries: Int,
    @ColumnInfo(name = "repetitionExercisePositionInTraining")
    override var positionInTraining: Int?,
    @ColumnInfo(name = "repetitionTrainingId")
    override val trainingId: Int?
): SeriesInterface
