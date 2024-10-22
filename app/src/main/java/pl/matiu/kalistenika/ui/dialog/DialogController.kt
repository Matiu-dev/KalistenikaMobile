package pl.matiu.testowa.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.ui.dialog.CreateTrainingDialog
import pl.matiu.kalistenika.ui.dialog.DeleteTrainingDialog

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
    navController: NavController
) {
    when (dialogResponse) {
        is DialogResponse.CreateTrainingDialog -> CreateTrainingDialog(
            showDialog = showDialog,
            onShowDialogChange = { onShowDialogChange(!showDialog) },
            DialogValues(title = "Create Dialog"),
            navController = navController,
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
            navController = navController,
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