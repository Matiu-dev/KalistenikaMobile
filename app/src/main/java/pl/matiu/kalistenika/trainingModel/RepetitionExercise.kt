package pl.matiu.kalistenika.trainingModel

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(    foreignKeys = [ForeignKey(
    entity = TrainingModel::class,  // Tabela, do której się odwołujemy
    parentColumns = ["trainingId"], // Kolumna z tabeli "parent" (TrainingModel)
    childColumns = ["repetitionTrainingId"],  // Kolumna z tabeli "child" (RepetitionExercise)
    onDelete = ForeignKey.CASCADE   // Co zrobić w przypadku usunięcia wpisu (tutaj kaskadowe usuwanie)
)],
    indices = [Index("repetitionTrainingId")] // Dodanie indeksu dla kolumny trainingId)
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
