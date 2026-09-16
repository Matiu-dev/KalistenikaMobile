package pl.matiu.testowa.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.composable.dialog.CreateTrainingDialog
import pl.matiu.kalistenika.composable.dialog.DeleteTrainingDialog

class DialogController {

    fun showDialog(dialogEnum: DialogEnum, training: TrainingModel): DialogResponse {
        return when (dialogEnum) {
            DialogEnum.CREATE_DIALOG -> DialogResponse.CreateTrainingDialog
            DialogEnum.DELETE_DIALOG -> DialogResponse.DeleteTrainingDialog(training)
            DialogEnum.UPDATE_DIALOG -> DialogResponse.EditTimeTrainingDialog(training)
        }
    }
}

@Composable
fun ShowTrainingDialog(
    dialogResponse: DialogResponse,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    backStack: NavBackStack<NavKey>
) {
    when (dialogResponse) {
        is DialogResponse.CreateTrainingDialog -> CreateTrainingDialog(
            showDialog = showDialog,
            onShowDialogChange = { onShowDialogChange(!showDialog) },
            DialogValues(title = "Create Dialog"),
            context = LocalContext.current
        )

        is DialogResponse.EditTimeTrainingDialog -> EditTrainingDialog(
            showDialog,
            { onShowDialogChange(!showDialog) },
            DialogValues(title = "Update dialog"),

        )

        is DialogResponse.DeleteTrainingDialog -> DeleteTrainingDialog(
            showDialog,
            { onShowDialogChange(!showDialog) },
            DialogValues(title = "Delete dialog"),
            training = dialogResponse.training
        )
    }
}

@Composable
fun EditTrainingDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    dialogValues: DialogValues,
) {

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onShowDialogChange(false)
            },

            title = { Text(text = dialogValues.title) },

            text = { Text(text = "") },

            dismissButton = {
                TextButton(
                    onClick = {
                        onShowDialogChange(false)
                    }
                ) {
                    Text(text = "Potwierdż")
                }
            },

            confirmButton = {
                Text(text = "Wyjdż")
            }
        )
    }
}