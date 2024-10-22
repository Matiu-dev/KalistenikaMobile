package pl.matiu.kalistenika.ui.dialog

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.testowa.dialog.DialogValues

@Composable
fun DeleteTrainingDialog(
    showDialog: Boolean,
    onShowDialogChange: (Boolean) -> Unit,
    dialogValues: DialogValues,
    navController: NavController,
    training: TrainingModel
) {

    if (showDialog) {
        AlertDialog(
            containerColor = InsideLevel1,
            onDismissRequest = {
                onShowDialogChange(false)
            },

            title = { Text(text = dialogValues.title) },

            text = { Text(text = "Czy chciałbyś usunąć trening?") },

            dismissButton = {
                TextButton(
                    onClick = {
                        onShowDialogChange(false)
                        TrainingViewModel().deleteTraining(training)
                        navController.navigate(MainRoutes.Training.destination)
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
                    Text(text = "Wyjdż", color = Smola)
                }
            }
        )
    }
}