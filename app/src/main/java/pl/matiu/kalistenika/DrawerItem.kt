package pl.matiu.kalistenika

import androidx.compose.material3.Card
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.viewModel.NinjaApiViewModel

@Composable
fun DrawerItem(cardTitle: String) {
    val apiNinjaViewModel = hiltViewModel<NinjaApiViewModel>()
    val exerciseList by apiNinjaViewModel.ninjaApiExerciseList.collectAsState()

    Card {
        NavigationDrawerItem(
            label = { Text(text = cardTitle) },
            selected = false,
            onClick = {
                apiNinjaViewModel.getExercises()
                RealTimeDatabaseService().saveExerciseToRealtimeDatabase(exerciseList)
            }
        )
    }
}