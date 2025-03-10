package pl.matiu.kalistenika.composable.language

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import pl.matiu.kalistenika.SelectLanguage
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.model.training.RepetitionExercise
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
fun ChangeLanguageScreen(context: Context, appLanguage: AppLanguage, navController: NavController) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LanguageButton("pl", context, appLanguage, navController)
            LanguageButton("en", context, appLanguage, navController)
        }
    }
}

@Composable
private fun LanguageButton(language: String, context: Context, appLanguage: AppLanguage, navController: NavController) {
    Button(
        onClick = {
            appLanguage.setLocalLanguage(language, context = context)
            appLanguage.changeLanguage(context = context)
            navController.navigate(MainRoutes.Training.destination)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
    ) {
        Text(text = if(language == "pl") "Ustaw język Polski" else "Ustaw język Angielski", color = Smola)
    }
}