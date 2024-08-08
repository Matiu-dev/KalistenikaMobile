package pl.matiu.kalistenika.training.model

data class RepetitionExercise(val exerciseId: Int, val exerciseName: String, val numberOfSeries: Int,
                         val numberOfReps: Int, val stopTimer: Boolean,
                         val breakBetweenSeries: Int,
    override val trainingId: Int?) : SeriesInterface
