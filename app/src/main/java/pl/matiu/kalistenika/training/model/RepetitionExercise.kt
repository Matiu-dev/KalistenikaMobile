package pl.matiu.kalistenika.training.model

data class RepetitionExercise(
    var exerciseId: Int, val exerciseName: String, val numberOfSeries: Int,
    val numberOfReps: Int, val stopTimer: Boolean,
    val breakBetweenSeries: Int,
    override var positionInTraining: Int?, override val trainingId: Int?
) : SeriesInterface
