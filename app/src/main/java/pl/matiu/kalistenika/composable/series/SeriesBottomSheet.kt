package pl.matiu.kalistenika.composable.series

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pl.matiu.kalistenika.ui.theme.MGreyDarker
//nieuzywane aktualnie
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeriesBottomSheet(
    showBottomSheet: Boolean,
    addTraining: Boolean,

    onShowBottomSheetChange: (Boolean) -> Unit,
    onAddTrainingChange: (Boolean) -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
//        Button(
//            onClick = { showBottomSheet = true }
//        ) {
//            Text("Display partial bottom sheet")
//        }

//        Row(modifier = Modifier.fillMaxWidth()) {
//            OutlinedButton(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(
//                        horizontal = 25.dp,
//                        vertical = 1.dp
//                    ),
//                onClick = { onAddTrainingChange(!addTraining) }
//            ) {
//                Text(
//                    text = "Dodaj ćwiczenie + $showBottomSheet",
//                    color = MGreyDarker
//                )
//            }
//        }

        if (showBottomSheet) {
            ModalBottomSheet(
                modifier = Modifier.fillMaxHeight(),
                sheetState = sheetState,
                onDismissRequest = { onShowBottomSheetChange(false) }
            ) {

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 25.dp,
                            vertical = 1.dp
                        ),
                    onClick = {
                        onAddTrainingChange(!addTraining)
                        onShowBottomSheetChange(false)
                    }
                ) {
                    Text(
                        text = "Dodaj ćwiczenie + $showBottomSheet",
                        color = MGreyDarker
                    )
                }
            }
        }
    }
}