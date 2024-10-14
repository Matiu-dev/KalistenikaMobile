package pl.matiu.kalistenika

import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.junit.Rule
import org.junit.Test
import pl.matiu.kalistenika.routes.MainRoutes
class BottomBarTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun bottomBarTest() {

        composeTestRule.setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = MainRoutes.Training.destination ) {
                composable(route = MainRoutes.Training.destination) {}
                composable(route = MainRoutes.History.destination) {}
            }

            BottomAppBar(navController = navController)
        }

        composeTestRule.onNodeWithText(MainRoutes.Training.title).performClick()
        Thread.sleep(1000)
        composeTestRule.onNodeWithText(MainRoutes.History.title).performClick()
        Thread.sleep(1000)
        composeTestRule.onNodeWithText(MainRoutes.History.title).assertIsSelected()
        Thread.sleep(2000)
    }
}