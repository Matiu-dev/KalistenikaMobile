package pl.matiu.kalistenika

import pl.matiu.kalistenika.training.model.SeriesInterface
import pl.matiu.kalistenika.training.model.TrainingModel
import pl.matiu.kalistenika.training.model.RepetitionExercise
import pl.matiu.kalistenika.training.model.TimeExercise

private var trainingList = mutableListOf<TrainingModel>()
private var seriesList = mutableListOf<SeriesInterface>()

fun setTrainings() {

    for(i in 1..20) {
        trainingList.add(TrainingModel(i, "Trening $i"))
    }
}

fun getTrainings(): List<TrainingModel> {
    return trainingList
}

fun getTrainingNameById(trainingId: Int): String? =
    trainingList.find { it.trainingId == trainingId }?.name

fun setSeries() {

//    seriesList.add(
//        RepetitionExercise("cwiczenie 1 na powtorzenia do treningu 1", 10,
//        10,true,
//        10, 1)
//    )
//
//    seriesList.add(        RepetitionExercise("cwiczenie 2 na powtorzenia do treningu 2", 10,
//        10,true,
//        10, 2)
//    )
//
//    seriesList.add(        RepetitionExercise("cwiczenie 3 na powtorzenia do treningu 2", 10,
//        10,true,
//        10, 2)
//    )
//
//    seriesList.add(        RepetitionExercise("cwiczenie 4 na powtorzenia do treningu 3", 10,
//        10,true,
//        10, 3)
//    )
//
//    seriesList.add(        RepetitionExercise("cwiczenie 5 na powtorzenia do treningu 3", 10,
//        10,true,
//        10, 3)
//    )
//
//    seriesList.add(        RepetitionExercise("cwiczenie 6 na powtorzenia do treningu 3", 10,
//        10,true,
//        10, 3)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 1 na czas do treningu 1",
//        10, 10,
//        10, 1)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 2 na czas do treningu 2",
//        10, 10,
//        10, 2)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 3 na czas do treningu 2",
//        10, 10,
//        10, 2)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 4 na czas do treningu 3",
//        10, 10,
//        10, 3)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 5 na czas do treningu 3",
//        10, 10,
//        10, 3)
//    )
//
//    seriesList.add(        TimeExercise("cwiczenie 6 na czas do treningu 3",
//        10, 10,
//        10, 3)
//    )
}

fun setOneSeries(series: SeriesInterface) {
    seriesList.add(series)
}

fun getSeries(): List<SeriesInterface> {
    return seriesList
}