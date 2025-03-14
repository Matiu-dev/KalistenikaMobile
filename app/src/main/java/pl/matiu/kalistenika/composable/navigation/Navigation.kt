package pl.matiu.kalistenika.composable.navigation

import android.content.Context
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import pl.matiu.kalistenika.R
import pl.matiu.kalistenika.composable.history.HistoryDetailsScreen
import pl.matiu.kalistenika.composable.history.HistoryScreen
import pl.matiu.kalistenika.composable.language.ChangeLanguageScreen
import pl.matiu.kalistenika.composable.series.CreateSeries
import pl.matiu.kalistenika.composable.series.RepetitionExerciseEditScreen
import pl.matiu.kalistenika.composable.series.SeriesScreen
import pl.matiu.kalistenika.composable.series.TimeExerciseEditScreen
import pl.matiu.kalistenika.composable.training.TrainingScreen
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.routes.MainRoutes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Navigation(navController: NavHostController,
               innerPadding: PaddingValues,
               context: Context,
               appLanguage: AppLanguage,
               onTopBarTitleChange: (String) -> Unit,
               onTopBarPreviewScreenChange: (String) -> Unit,
               onIsNavigationIconChange: (Boolean) -> Unit,
               onAddButtonChange: (String) -> Unit,
               trainingId: Int,
               onTrainingIdChange: (Int) -> Unit,
               trainingName: String,
               onTrainingNameChange: (String) -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = MainRoutes.Training.destination,
        modifier = Modifier.padding(innerPadding),
    ) {
        composable(route = AlternativeRoutes.ChangeLanguage.destination) {
            onTopBarTitleChange(stringResource(R.string.language_page_title))
            onTopBarPreviewScreenChange(AlternativeRoutes.ChangeLanguage.topBarPreviewScreen)
            onIsNavigationIconChange(AlternativeRoutes.ChangeLanguage.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.ChangeLanguage.addButton)

            ChangeLanguageScreen(context = context, appLanguage = appLanguage, navController = navController)
        }

        composable(route = MainRoutes.Training.destination) {
            onTopBarTitleChange(stringResource(R.string.main_page_title))
            onTopBarPreviewScreenChange(MainRoutes.Training.destination)
            onIsNavigationIconChange(MainRoutes.Training.isNavigationIcon)
            onAddButtonChange(MainRoutes.Training.addButton)

            TrainingScreen(navController = navController)
        }

        composable(route = AlternativeRoutes.SeriesScreen.destination + "/{trainingName}" + "/{trainingId}") { backStackEntry ->

            onTrainingIdChange(backStackEntry.arguments?.getString("trainingId")?.toInt() ?: 0)
            onTrainingNameChange(backStackEntry.arguments?.getString("trainingName") ?: "")

            onTopBarTitleChange(backStackEntry.arguments?.getString("trainingName").toString())
            onTopBarPreviewScreenChange(AlternativeRoutes.SeriesScreen.topBarPreviewScreen)
            onIsNavigationIconChange(AlternativeRoutes.SeriesScreen.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.SeriesScreen.addButton)

            SeriesScreen(
                navController = navController,
                trainingName = backStackEntry.arguments?.getString("trainingName").toString()
            )
        }

        composable(route = AlternativeRoutes.CreateSeries.destination + "/{trainingId}") { backStackEntry ->
            onTrainingIdChange(backStackEntry.arguments?.getString("trainingId")?.toInt()!!)

            onTopBarTitleChange(AlternativeRoutes.CreateSeries.topBarTitle)
            onTopBarPreviewScreenChange(AlternativeRoutes.CreateSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}")
            onIsNavigationIconChange(AlternativeRoutes.CreateSeries.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.CreateSeries.addButton)

            CreateSeries(
                navController = navController,
                trainingId = trainingId,
                context = LocalContext.current,
                trainingName = trainingName
            )
        }

        composable(route = AlternativeRoutes.EditRepetitionSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
            onTrainingIdChange(backStackEntry.arguments?.getString("trainingId")?.toInt()!!)
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

            onTopBarTitleChange(AlternativeRoutes.EditRepetitionSeries.topBarTitle)
            onTopBarPreviewScreenChange(AlternativeRoutes.EditRepetitionSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}")
            onIsNavigationIconChange(AlternativeRoutes.EditRepetitionSeries.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.EditRepetitionSeries.addButton)

            RepetitionExerciseEditScreen(
                navigator = navController,
                exerciseId = exerciseId,
                trainingId = trainingId
            )
        }

        composable(route = AlternativeRoutes.EditTimeSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
            onTrainingIdChange(backStackEntry.arguments?.getString("trainingId")?.toInt()!!)
            val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

            onTopBarTitleChange(AlternativeRoutes.EditTimeSeries.topBarTitle)
            onTopBarPreviewScreenChange(AlternativeRoutes.EditTimeSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}")
            onIsNavigationIconChange(AlternativeRoutes.EditTimeSeries.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.EditTimeSeries.addButton)

            TimeExerciseEditScreen(
                navigator = navController,
                exerciseId = exerciseId,
                trainingId = trainingId
            )
        }


        composable(route = MainRoutes.History.destination) {
            onTopBarTitleChange(MainRoutes.History.topBarTitle)
            onIsNavigationIconChange(MainRoutes.History.isNavigationIcon)
            onAddButtonChange(MainRoutes.History.addButton)

            HistoryScreen(navController = navController)
        }

        composable(route = AlternativeRoutes.HistoryDateDetails.destination + "/{date}") {

            val date: String = it.arguments?.getString("date").toString()

            onTopBarTitleChange(AlternativeRoutes.HistoryDateDetails.topBarTitle)
            onTopBarPreviewScreenChange(AlternativeRoutes.HistoryDateDetails.topBarPreviewScreen)
            onIsNavigationIconChange(AlternativeRoutes.HistoryDateDetails.isNavigationIcon)
            onAddButtonChange(AlternativeRoutes.HistoryDateDetails.addButton)

            HistoryDetailsScreen(date = date)
        }
    }
}