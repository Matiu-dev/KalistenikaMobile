package pl.matiu.kalistenika.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import pl.matiu.kalistenika.room.ExerciseDao
import pl.matiu.kalistenika.room.ExerciseDatabase
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.room.TrainingDao
import pl.matiu.kalistenika.room.TrainingDatabaseService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class TrainingModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): ExerciseDatabase {
        return Room.databaseBuilder(
            appContext,
            ExerciseDatabase::class.java,
            "Training_DB"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTrainingDao(database: ExerciseDatabase): TrainingDao {
        return database.getTrainingDao()
    }

    @Provides
    @Singleton
    fun provideExerciseDao(database: ExerciseDatabase): ExerciseDao {
        return database.getExerciseDao()
    }

    @Provides
    @Singleton
    fun provideTrainingDatabaseService(trainingDao: TrainingDao, exerciseDao: ExerciseDao): TrainingDatabaseService {
        return TrainingDatabaseService(trainingDao, exerciseDao)
    }

    @Provides
    @Singleton
    fun provideExerciseDatabaseService(exerciseDao: ExerciseDao): ExerciseDatabaseService {
        return ExerciseDatabaseService(exerciseDao)
    }
}