package pl.matiu.kalistenika.trainingModel

data class TimeExercise(
    var exerciseId: Int, val exerciseName: String, val numberOfSeries: Int,
    val timeForSeries: Int, val breakBetweenSeries: Int, override var positionInTraining: Int?,
    override val trainingId: Int?
) : SeriesInterface