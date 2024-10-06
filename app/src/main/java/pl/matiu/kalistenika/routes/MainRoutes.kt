package pl.matiu.kalistenika.routes

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.graphics.vector.ImageVector

enum class MainRoutes(
    val icon: ImageVector,
    val title: String,
    val destination: String,
    val topBarTitle: String,
    val isNavigationIcon: Boolean,
    val addButton: String
) {
    Training(
        icon = Icons.Filled.Build,
        title = "Trening",
        destination = "training",
        topBarTitle = "Treningi",
        isNavigationIcon = false,
        addButton = "AddTraining"
    ),

    History(
        icon = Icons.Filled.DateRange,
        title = "Historia",
        destination = "history",
        topBarTitle = "Historia",
        isNavigationIcon = false,
        addButton = ""
    ),

//    UserProfile(
//        icon = Icons.Filled.Person,
//        text = "Profil",
//        destination = "user_profile"
//    )
}