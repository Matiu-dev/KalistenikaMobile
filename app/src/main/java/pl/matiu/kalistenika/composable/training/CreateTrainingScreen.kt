package pl.matiu.kalistenika.composable.training

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import pl.matiu.kalistenika.testTags.TRAINING_NAME_IN_ADD_TRAINING_SCREEN_TEST_TAG
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
fun CreateTraining(name: String,
                   onNameChange: (String) -> Unit) {

//    var trainingName by remember { mutableStateOf("") }

    Surface(
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
                    value = name,
                    onValueChange = { onNameChange(it) },
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Smola,
                        unfocusedTextColor = Smola,
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        cursorColor = Smola,
                        focusedIndicatorColor = Smola,
                        unfocusedIndicatorColor = Smola
                    ),
                    modifier = Modifier.padding(horizontal = 5.dp)
                        .testTag(TRAINING_NAME_IN_ADD_TRAINING_SCREEN_TEST_TAG)
                )

            }

//            Row(
//                modifier = Modifier.padding(horizontal = 5.dp, vertical = 5.dp),
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//
//                Button(
//                    onClick = {
//                        if(trainingName.isNotBlank()) {
//                            TrainingViewModel().addTraining(TrainingModel(name = trainingName))
//                            navController.navigate(MainRoutes.Training.destination)
//                        } else {
//                            Toast.makeText(context, "Popraw dane", Toast.LENGTH_SHORT).show()
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 5.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
//                ) {
//                    Text(text = "Dodaj trening", color = Smola)
//                }
//
//            }


        }
    }
}