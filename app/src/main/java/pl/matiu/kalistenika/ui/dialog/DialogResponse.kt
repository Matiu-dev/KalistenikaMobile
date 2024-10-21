package pl.matiu.testowa.dialog

import pl.matiu.kalistenika.model.training.TimeExercise

sealed class DialogResponse {
    object CreateExerciseDialog: DialogResponse()
    data class EditTimeExerciseDialog(val exercise: TimeExercise): DialogResponse()
    data class DeleteTimeExerciseDialog(val exercise: TimeExercise): DialogResponse()
}