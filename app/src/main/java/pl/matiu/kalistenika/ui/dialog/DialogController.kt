package pl.matiu.testowa.dialog

import android.widget.EditText
import android.widget.TextView
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
import pl.matiu.kalistenika.model.training.SeriesInterface
import pl.matiu.kalistenika.model.training.TimeExercise

class DialogController {

    fun showDialog(dialogEnum: DialogEnum, exercise: TimeExercise): DialogResponse {
        return when (dialogEnum) {
            DialogEnum.CREATE_DIALOG -> DialogResponse.CreateExerciseDialog
            DialogEnum.DELETE_DIALOG -> DialogResponse.DeleteTimeExerciseDialog(exercise)
            DialogEnum.UPDATE_DIALOG -> DialogResponse.EditTimeExerciseDialog(exercise)
        }
    }
}

@Composable
fun ShowDialog(
    dialogResponse: DialogResponse,
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit
) {
    when (dialogResponse) {
        is DialogResponse.CreateExerciseDialog -> CreateExerciseDialog(
            showDialog,
            { onShowDialogChange(!showDialog) },
            DialogValues(title = "Create Dialog"),

        )

        is DialogResponse.EditTimeExerciseDialog -> EditExerciseDialog(
            showDialog,
            { onShowDialogChange(!showDialog) },
            DialogValues(title = "Update dialog"),

        )

        is DialogResponse.DeleteTimeExerciseDialog -> DeleteExerciseDialog(
            showDialog,
            { onShowDialogChange(!showDialog) },
            DialogValues(title = "Delete dialog"),

        )
    }
}

@Composable
fun CreateExerciseDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    dialogValues: DialogValues,
) {

    val name = rememberSaveable { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = {
                onShowDialogChange(false)
            },

            title = {
                Text(text = dialogValues.title)
            },

            text = {
                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)) {
                    Text(text = "Podaj nazwe")
                    TextField(value = name.value,
                        onValueChange = {name.value = it}
                    )
                }
            },

            dismissButton = {
                TextButton(
                    onClick = {
                        onShowDialogChange(false)
                    }
                ) {
                    Text(text = "OK")
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {}
                ) {
                    Text(text = "Wyjdż")
                }
            }
        )
    }
}

@Composable
fun EditExerciseDialog(
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

@Composable
fun DeleteExerciseDialog(
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