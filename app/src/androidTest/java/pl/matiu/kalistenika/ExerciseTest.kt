package pl.matiu.kalistenika

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import pl.matiu.kalistenika.model.training.TrainingModel
import pl.matiu.kalistenika.room.ExerciseDao
import pl.matiu.kalistenika.room.ExerciseDatabase
import pl.matiu.kalistenika.room.TrainingDao
import pl.matiu.kalistenika.viewModel.TrainingViewModel

@RunWith(AndroidJUnit4::class)
class ExerciseTest {

    lateinit var trainingDao: TrainingDao

    lateinit var exerciseDao: ExerciseDao

    private lateinit var db: ExerciseDatabase

    @Before
    fun setup() {

        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ExerciseDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        trainingDao = db.getTrainingDao()
        exerciseDao = db.getExerciseDao()

    }

    @After
    fun closeTest() {
        db.close()
    }

    @Test
    fun createExercise() {
        val trainingModel = TrainingModel(1, "nazwa cwiczenia")
        trainingDao.addTraining(trainingModel)

        val allTrainings = trainingDao.getAllTrainings()
        assertEquals(listOf(trainingModel), allTrainings)
    }
}