package pl.matiu.kalistenika

import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsSelected
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.routes.MainRoutes
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class BottomBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun bottomBarTest() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(MainRoutes.Training.title).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText(MainRoutes.History.title).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNode(
            hasText(MainRoutes.History.title) and
                    SemanticsMatcher.expectValue(SemanticsProperties.Selected, true)
        ).assertExists()
        composeTestRule.waitForIdle()
    }
}