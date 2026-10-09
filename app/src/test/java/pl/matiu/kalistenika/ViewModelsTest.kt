package pl.matiu.kalistenika

import app.cash.turbine.test
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import org.junit.Assert.assertFalse

@OptIn(ExperimentalCoroutinesApi::class)
class ViewModelsTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val service: TrainingDatabaseService = mockk()

    @Test
    fun `load trainings`() = runTest {
        // given
        val trainings = listOf(TrainingModel(name = "Push day"))
        every { service.getAllTraining() } returns trainings

        // when
        val viewModel = TrainingViewModel(service, mainDispatcherRule.testDispatcher)

        // then
        viewModel.trainingList.test {
            assertEquals(trainings, awaitItem())
        }

        viewModel.isTrainingLoading.test {
            assertEquals(false, awaitItem())
        }

        verify(exactly = 1) { service.getAllTraining() }
    }

    @Test
    fun `create training`() = runTest {

        //given
        val trainingModel = TrainingModel(name = "Push day")

        every { service.getAllTraining() } returnsMany listOf(//w inicie jest getAllTraining dlatego pierwsza zwrtoka jest emptyList
            emptyList(),
            listOf(trainingModel)
        )

        justRun { service.addTraining(any()) }//przy kazdym wywolaniu funkcji, nie wykonuje zawartosci funkcji

        val viewModel = TrainingViewModel(service, mainDispatcherRule.testDispatcher)//pierwsze wywolanie getAllTraining w init
        advanceUntilIdle()//wykonuje zalegle coroutiny
        assertEquals(emptyList<TrainingModel>(), viewModel.trainingList.value)

        //when
        viewModel.addTraining(trainingModel)//tutaj drugie wywolanie service.getAllTraining() wedlug every, oraz pominiecie wykonania service.addTraining
        advanceUntilIdle()

        //then
        assertEquals(listOf(trainingModel), viewModel.trainingList.value)//sprawdza druga zwrotke getAllTraining
        assertEquals(false, viewModel.isTrainingLoading.value)
        verify(exactly = 1) { service.addTraining(trainingModel) }

    }

    @Test
    fun `delete training`() = runTest {
        //given
        val trainingModel = TrainingModel(name = "Push day")

        every { service.getAllTraining() } returnsMany listOf(//w inicie jest getAllTraining dlatego pierwsza zwrtoka jest emptyList
            listOf(trainingModel),//najpierws jest cos
            emptyList()//kolejne wywolanie to usuniecie
        )

        justRun { service.deleteTraining(any()) }//przy kazdym wywolaniu funkcji, nie wykonuje zawartosci funkcji

        val viewModel = TrainingViewModel(service, mainDispatcherRule.testDispatcher)//pierwsze wywolanie getAllTraining w init
        advanceUntilIdle()//wykonuje zalegle coroutiny
        assertEquals(listOf(trainingModel), viewModel.trainingList.value)

        //when
        viewModel.deleteTraining(trainingModel)//tutaj drugie wywolanie service.getAllTraining() wedlug every, oraz pominiecie wykonania service.addTraining
        advanceUntilIdle()

        //then
        assertEquals(emptyList<TrainingModel>(), viewModel.trainingList.value)//sprawdza druga zwrotke getAllTraining
        assertEquals(false, viewModel.isTrainingLoading.value)
        verify(exactly = 1) { service.deleteTraining(trainingModel) }

    }
}