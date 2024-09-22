package pl.matiu.kalistenika.internalStorage

import android.content.Context
import kotlinx.coroutines.delay
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.Logger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.trainingModel.SeriesInterface


class LoadDataFromLocalMemory {

    private var logger: Logger = ThreadIdLogger(ConsoleLogger())
    suspend fun loadDataFromLocalMemory(context: Context, trainingId: Int?): List<SeriesInterface> {

        logger.log("loading data from local memory")

//        delay(5000)

        var timeExerciseList: List<SeriesInterface> =
            TimeExerciseInternalStorage().loadTimeExerciseFromInternalStorage(
                context,
                TrainingInternalStorageService().getTrainingNameById(context, trainingId))
                .filter { v -> v.trainingId == trainingId }

        var repetitionExerciseList: List<SeriesInterface> =
            RepetitionExerciseInternalStorage().loadRepetitionExerciseFromInternalStorage(
                context,
                TrainingInternalStorageService().getTrainingNameById(context, trainingId))
                .filter { v -> v.trainingId == trainingId }

        return ( timeExerciseList + repetitionExerciseList ).sortedBy { it.positionInTraining }
    }
}
