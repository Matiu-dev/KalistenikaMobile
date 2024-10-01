package pl.matiu.kalistenika

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

//interface Destination {
//
//    val icon: ImageVector
//    val route: String
//}
//
//object Training : Destination {
//    override val icon = Icons.Filled.Build
//    override val route = "training"
//}
//
//object History : Destination {
//    override val icon = Icons.Filled.DateRange
//    override val route = "history"
//}
//
//object UserProfile : Destination {
//    override val icon = Icons.Filled.Person
//    override val route = "user_profile"
//}

enum class Routes(
    val icon: ImageVector,
    val text: String,
    val destination: String
) {
    Training(
        icon = Icons.Filled.Build,
        text = "Trening",
        destination = "training"
    ),

    History(
        icon = Icons.Filled.DateRange,
        text = "Historia",
        destination = "history"
    ),

//    UserProfile(
//        icon = Icons.Filled.Person,
//        text = "Profil",
//        destination = "user_profile"
//    )
}