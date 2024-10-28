package pl.matiu.kalistenika.routes

enum class AlternativeRoutes(
    val destination: String,
    val topBarTitle: String,
    val topBarPreviewScreen: String,
    val isNavigationIcon: Boolean,
    val addButton: String
) {

    SeriesScreen(
        destination = MainRoutes.Training.destination,
        topBarTitle = "Kreator treningu",
        topBarPreviewScreen = MainRoutes.Training.destination,
        isNavigationIcon = true,
        addButton = "AddExercise"
    ),

    CreateSeries(
        destination = MainRoutes.Training.destination + "/createSeries",
        topBarTitle = "Kreator serii",
        topBarPreviewScreen = SeriesScreen.destination,
        isNavigationIcon = true,
        addButton = ""
    ),

    EditTimeSeries(
        destination = MainRoutes.Training.destination + "/editTimeExercise",
        topBarTitle = "Edytor ćwiczenia",
        topBarPreviewScreen = SeriesScreen.destination,
        isNavigationIcon = true,
        addButton = ""
    ),

    EditRepetitionSeries(
        destination = MainRoutes.Training.destination + "/editRepetitionExercise",
        topBarTitle = "Edytor ćwiczenia",
        topBarPreviewScreen = SeriesScreen.destination,
        isNavigationIcon = true,
        addButton = ""
    ),

    HistoryDateDetails(
        destination = MainRoutes.History.destination + "/historyDetails",
        topBarTitle = "Szczegóły",
        topBarPreviewScreen = MainRoutes.History.destination,
        isNavigationIcon = true,
        addButton = ""
    )

}