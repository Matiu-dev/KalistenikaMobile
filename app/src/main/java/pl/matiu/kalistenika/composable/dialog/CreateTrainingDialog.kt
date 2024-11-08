package pl.matiu.kalistenika.composable.dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.composable.training.CreateTraining
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.testowa.dialog.DialogValues

@Composable
fun CreateTrainingDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    dialogValues: DialogValues,
    navController: NavController,
    context: Context
) {

    val name = rememberSaveable { mutableStateOf("") }

    val trainingViewModel: TrainingViewModel = hiltViewModel<TrainingViewModel>()

    if (showDialog) {
        AlertDialog(
            containerColor = InsideLevel1,
            onDismissRequest = {
                onShowDialogChange(false)
            },

            title = {
                Text(text = dialogValues.title)
            },

            text = {
//                Column(modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp)) {
//                    Text(text = "Podaj nazwe")
//                    TextField(value = name.value,
//                        onValueChange = {name.value = it}
//                    )
//                }
                CreateTraining(name = name.value, onNameChange = {name.value = it})
            },

            dismissButton = {
                TextButton(
                    onClick = {

                        if (name.value.isNotBlank()) {
                            trainingViewModel.addTraining(TrainingModel(name = name.value))
                            navController.navigate(MainRoutes.Training.destination)
                            onShowDialogChange(false)
                        } else {
                            Toast.makeText(context, "Popraw dane", Toast.LENGTH_SHORT).show()
                        }

                    }
                ) {
                    Text(text = "Potwierdż", color = Smola)
                }
            },

            confirmButton = {
                TextButton(
                    onClick = {
                        onShowDialogChange(false)
                    }
                ) {
                    Text(text = "Wyjdż",  color = Smola)
                }
            }
        )
    }
}