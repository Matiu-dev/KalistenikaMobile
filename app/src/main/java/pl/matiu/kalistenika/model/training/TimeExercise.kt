package pl.matiu.kalistenika.model.training

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(
    entity = TrainingModel::class,
    parentColumns = ["trainingId"],
    childColumns = ["timeTrainingId"],
    onDelete = ForeignKey.CASCADE
)],
    indices = [Index("timeTrainingId")]
)
data class TimeExercise(
    @PrimaryKey(autoGenerate = true)
    val timeExerciseId: Int = 0,
    val timeExerciseName: String,
    val numberOfTimeSeries: Int,
    val timeForTimeSeries: Int,
    val breakBetweenTimeSeries: Int,
    @ColumnInfo(name = "timeExercisePositionInTraining")
    override var positionInTraining: Int?,
    @ColumnInfo(name = "timeTrainingId")
    override val trainingId: Int?
): SeriesInterface