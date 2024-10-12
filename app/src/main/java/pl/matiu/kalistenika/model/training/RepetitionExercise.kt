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
    val repetitionExerciseName: String = "",
    val numberOfRepetitionSeries: Int = 0,
    val numberOfReps: Int = 0,
    val stopTimer: Boolean = false,
    val breakBetweenRepetitionSeries: Int = 0,
    @ColumnInfo(name = "repetitionExercisePositionInTraining")
    override var positionInTraining: Int? = null,
    @ColumnInfo(name = "repetitionTrainingId")
    override val trainingId: Int? = null
): SeriesInterface
