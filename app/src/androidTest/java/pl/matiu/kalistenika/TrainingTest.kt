package pl.matiu.kalistenika

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.dialog.CreateTrainingDialog
import pl.matiu.testowa.dialog.DialogValues


class TrainingTest {

    @get:Rule
    val trainingTestRule = createComposeRule()

    @Test
    fun createTrainingTest() {
        trainingTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = MainRoutes.Training.destination ) {
                composable(route = MainRoutes.Training.destination) {}
                composable(route = MainRoutes.History.destination) {}
            }

            CreateTrainingDialog(
                showDialog = true,
                onShowDialogChange = { },
                dialogValues = DialogValues(title = "Create Dialog"),
                navController = navController,
                context = LocalContext.current
            )
        }
    }
}