package pl.matiu.kalistenika

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.MainScreenColor
import pl.matiu.kalistenika.ui.theme.Wheat

@Composable
fun BottomAppBar(navController: NavController) {

    var tabIndex by remember { mutableStateOf(0) }
    val tabsScreen = MainRoutes.values().toList()

    BottomAppBar(
        containerColor = MainScreenColor,
        contentColor = MainScreenColor,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MainScreenColor)
    ) {
        TabRow(
            divider = {},
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                if (tabIndex < tabPositions.size) {
                    TabRowDefaults.Indicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = Wheat
                    )
                }
            }
        ) {
            tabsScreen.forEachIndexed { index, dest ->
                Tab(
                    modifier = Modifier.background(color = MainScreenColor),
                    text = { Text(dest.title, color = Wheat) },
                    selected = tabIndex == index,
                    onClick = {
                        tabIndex = index
                        navController.navigate(dest.destination)
                    },
                    icon = {
                        Icon(
                            imageVector = dest.icon,
                            contentDescription = "icon",
                            tint = Wheat
                        )
                    }
                )
            }
        }
    }
}