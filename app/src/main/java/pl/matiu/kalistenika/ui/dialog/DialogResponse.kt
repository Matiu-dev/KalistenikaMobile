package pl.matiu.testowa.dialog

import pl.matiu.kalistenika.model.training.TimeExercise
import pl.matiu.kalistenika.model.training.TrainingModel

sealed class DialogResponse {
    object CreateTrainingDialog: DialogResponse()
    data class EditTimeTrainingDialog(val training: TrainingModel): DialogResponse()
    data class DeleteTrainingDialog(val training: TrainingModel): DialogResponse()
}