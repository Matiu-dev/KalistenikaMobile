package pl.matiu.kalistenika

import android.Manifest
import androidx.compose.ui.semantics.SemanticsProperties
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.testTags.ACCEPT_BUTTON_IN_ADD_TRAINING_SCREEN_TEST_TAG
import pl.matiu.kalistenika.testTags.TRAINING_NAME_IN_ADD_TRAINING_SCREEN_TEST_TAG

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class BottomBarTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule(order = 2)
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(Manifest.permission.POST_NOTIFICATIONS)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun test1_bottomBarTest() {
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

    @Test
    fun test2_createTraining() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("add training").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Create Dialog").assertExists()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(TRAINING_NAME_IN_ADD_TRAINING_SCREEN_TEST_TAG).performTextInput("test")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag(ACCEPT_BUTTON_IN_ADD_TRAINING_SCREEN_TEST_TAG).performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test").assertIsDisplayed()
    }

    @Test
    fun test3_createTimeExercise() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("add exercise").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("showTimeSeriesContent").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("timeExerciseNameEditText").performTextInput("time exercise")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("timeExerciseNumberOfSeriesEditText").performTextInput("3")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("timeExerciseTimeForSeriesEditText").performTextInput("60")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("timeExerciseBreakBetweenSeriesEditText").performTextInput("30")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("createTimeExerciseButton").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("time exercise").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Ilość serii: 3").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Czas dla serii: 60").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Przerwa między seriami: 30").assertIsDisplayed()
        composeTestRule.waitForIdle()
    }

    @Test
    fun test4_createRepetitionExercise() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("add exercise").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("showRepetitionSeriesContent").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repetitionExerciseNameEditText").performTextInput("repetition exercise")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repetitionExerciseNumberOfSeriesEditText").performTextInput("5")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repetitionExerciseBreakBetweenSeriesEditText").performTextInput("120")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("repetitionExerciseNumberOfRepsEditText").performTextInput("10")
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("createRepetitionExerciseButton").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithContentDescription("next").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("repetition exercise").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Ilość serii: 5").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Przerwa między seriami: 120").assertIsDisplayed()
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("Liczba powtórzeń w serii: 10").assertIsDisplayed()
        composeTestRule.waitForIdle()
    }

    @Test
    fun test5_editTimeExercise() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test").performClick()
        composeTestRule.waitForIdle()
    }

    @Test
    fun test6_editRepetitionExercise() {
        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithText("test").performClick()
        composeTestRule.waitForIdle()
    }
}