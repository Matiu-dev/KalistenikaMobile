package pl.matiu.kalistenika.training

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.ui.theme.Beige
import pl.matiu.kalistenika.ui.theme.InsideLevel0Background
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTraining(navController: NavController, context: Context) {

    var trainingName by remember { mutableStateOf("") }

    Surface(
//        color = InsideLevel1,
        modifier = Modifier
            .fillMaxSize(),
        color = InsideLevel0Background
    ) {
        Column() {

            Surface(modifier = Modifier.padding(vertical = 5.dp)) {
                Column(Modifier.background(InsideLevel1)) {
                    Row(modifier = Modifier.padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {

                        Text(
                            text = "Nazwa treningu",
                            color = Smola,
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )

                        TextField(
                            value = trainingName,
                            onValueChange = { trainingName = it },
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = Color.Transparent,
                                cursorColor = Smola,
                                focusedIndicatorColor = Smola,
                                unfocusedIndicatorColor = Smola,
                                focusedTextColor = Smola,
                                unfocusedTextColor = Smola
                            ),
                            modifier = Modifier.padding(horizontal = 5.dp)
                        )

                    }

                    Row(modifier = Modifier.padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically) {

                        Button(
                            onClick = {
                                TrainingInternalStorageService().saveTrainingToInternalStorage(context = context, trainingName)
                                navController.navigate(Training.route)
                            },
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 5.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                        ) {
                            Text(text = "Dodaj trening", color = Smola)
                        }

                    }
                }

            }

        }
    }
}