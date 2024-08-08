package pl.matiu.kalistenika.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

interface KalistenikaDestination {

    val icon: ImageVector
    val route: String
}

object Training: KalistenikaDestination {
    override val icon = Icons.Filled.Build
    override val route = "training"
}

object History: KalistenikaDestination {
    override val icon = Icons.Filled.DateRange
    override val route = "history"
}

object UserProfile: KalistenikaDestination {
    override val icon = Icons.Filled.Person
    override val route = "user_profile"
}

val kalistenikaTabRowScreens = listOf(Training, History, UserProfile)