package pl.matiu.kalistenika.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateTraining(navController: NavController) {

    var trainingName by remember { mutableStateOf("") }

    val trainingViewModel: TrainingViewModel = viewModel()
    val trainingList2 by trainingViewModel.trainingList.collectAsState()


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = InsideLevel1
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
                .border(1.dp, Smola, RoundedCornerShape(8.dp)).fillMaxWidth(),
        ) {

            Row(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically,

                ) {

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

            Row(
                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = {
                        TrainingDatabaseService().addTraining(name = trainingName)
                        navController.navigate(MainRoutes.Training.destination)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
                ) {
                    Text(text = "Dodaj trening", color = Smola)
                }

            }


        }
    }
}