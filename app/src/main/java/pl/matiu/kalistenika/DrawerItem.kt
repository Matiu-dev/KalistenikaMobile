package pl.matiu.kalistenika

import android.app.Activity
import android.app.LocaleManager
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.os.LocaleListCompat
import androidx.hilt.navigation.compose.hiltViewModel
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
import pl.matiu.kalistenika.viewModel.NinjaApiViewModel
import java.util.Locale

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
fun SelectLanguage(language: String, context: Context) {

    Card {
        NavigationDrawerItem(
            label = { Text(text = language) },
            selected = false,
            onClick = {
                when(language) {
                    "Polski" ->  {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            context.getSystemService(LocaleManager::class.java)?.applicationLocales = LocaleList.forLanguageTags("pl")
                        } else {
                            changeLanguageOnApiBeforeTiramisu(context = context, language = "pl")
                        }
                    }
                    "Angielski" -> {
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            context.getSystemService(LocaleManager::class.java)?.applicationLocales = LocaleList.forLanguageTags("en")
                        } else {
                            changeLanguageOnApiBeforeTiramisu(context = context, language = "en")
                        }
                    }
                }
            }
        )
    }
}

private fun changeLanguageOnApiBeforeTiramisu(context: Context, language: String) {
    val locale = Locale(language)
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)
    configuration.setLayoutDirection(locale)
    context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    context.createConfigurationContext(configuration)
}