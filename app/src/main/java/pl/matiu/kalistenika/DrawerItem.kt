package pl.matiu.kalistenika

import android.content.Context
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.routes.MainRoutes
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

@Composable
fun SelectLanguage(language: String, context: Context?, appLanguage: AppLanguage, navController: NavController) {

    Card {
        NavigationDrawerItem(
            label = { Text(text = language) },
            selected = false,
            onClick = {
                when(language) {
                    "Polski" ->  {
                        appLanguage.setLocalLanguage("pl", context = context)
                        appLanguage.changeLanguage(context = context)
                        navController.navigate(MainRoutes.Training.destination)
                    }
                    "Angielski" -> {
                        appLanguage.setLocalLanguage("en", context = context)
                        appLanguage.changeLanguage(context = context)
                        navController.navigate(MainRoutes.Training.destination)
                    }
                }
            }
        )
    }
}