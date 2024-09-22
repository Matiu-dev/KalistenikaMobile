package pl.matiu.kalistenika.ui

import androidx.compose.material3.Card
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService

@Composable
fun DrawerItem(cardTitle: String) {

    Card {
        NavigationDrawerItem(
            label = { Text(text = cardTitle) },
            selected = false,
            onClick = {
                RealTimeDatabaseService().writeData()
            }
        )
    }
}