package pl.matiu.kalistenika.trainingModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrainingModel(
    @PrimaryKey(autoGenerate = true)
    val trainingId: Int = 0,
    val name: String
)