package pl.matiu.kalistenika

import android.util.Log
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
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import pl.matiu.kalistenika.composable.navigation.Route
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.MainScreenColor
import pl.matiu.kalistenika.ui.theme.Wheat

@Composable
fun BottomAppBar(backStack: NavBackStack<NavKey>) {

    var tabIndex by remember { mutableStateOf(0) }
    val tabsScreen = MainRoutes.entries

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
                        Log.d("log", backStack.get(0).toString())
                        tabIndex = index
                        if(backStack.get(0) == Route.TrainingScreen && index != 0) {
                            backStack.clear()
                            backStack.add(Route.HistoryScreen)
                        } else if(backStack.get(0) == Route.HistoryScreen && index != 1) {
                            backStack.clear()
                            backStack.add(Route.TrainingScreen)
                        }
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