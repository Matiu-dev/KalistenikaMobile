package pl.matiu.kalistenika.composable.language

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.InsideLevel2
import pl.matiu.kalistenika.ui.theme.Smola

@Composable
fun ChangeLanguageScreen(
    appLanguage: AppLanguage,
    onUpdateTopBar: (title: String, preview: String, icon: Boolean, button: String) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        onUpdateTopBar(
            context.getString(R.string.language_page_title),
            AlternativeRoutes.ChangeLanguage.topBarPreviewScreen,
            AlternativeRoutes.ChangeLanguage.isNavigationIcon,
            AlternativeRoutes.ChangeLanguage.addButton
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = InsideLevel1
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LanguageButton("pl", context, appLanguage)
            LanguageButton("en", context, appLanguage)
        }
    }
}

@Composable
private fun LanguageButton(language: String, context: Context, appLanguage: AppLanguage) {
    Button(
        onClick = {
            appLanguage.setLocalLanguage(language, context = context)
            appLanguage.changeLanguage(context = context)
//            navController.navigate(MainRoutes.Training.destination)
        },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = InsideLevel2)
    ) {
        Text(text = if(language == "pl") "Ustaw język Polski" else "Ustaw język Angielski", color = Smola)
    }
}