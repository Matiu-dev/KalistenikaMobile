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
    val timeExerciseName: String = "",
    val numberOfTimeSeries: Int = 0,
    val timeForTimeSeries: Int = 0,
    val breakBetweenTimeSeries: Int = 0,
    @ColumnInfo(name = "timeExercisePositionInTraining")
    override var positionInTraining: Int? = null,
    @ColumnInfo(name = "timeTrainingId")
    override val trainingId: Int? = null
): SeriesInterface