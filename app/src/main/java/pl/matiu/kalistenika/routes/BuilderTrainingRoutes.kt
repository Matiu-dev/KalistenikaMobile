package pl.matiu.kalistenika.routes

import pl.matiu.kalistenika.routes.Destination.Companion.generateRoute

val Training_Route = generateRoute(MainRoutes.Training.destination)

val Series_Route = generateRoute(AlternativeRoutes.SeriesScreen.destination)

val Create_Series_Route = generateRoute(AlternativeRoutes.SeriesScreen.destination)

val Edit_Time_Series_Route = generateRoute(AlternativeRoutes.SeriesScreen.destination)

val Edit_Repetition_Series_Route = generateRoute(AlternativeRoutes.SeriesScreen.destination)