package pl.matiu.kalistenika

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.ui.NavDisplay
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.composable.history.HistoryDetailsScreen
import pl.matiu.kalistenika.composable.history.HistoryScreen
import pl.matiu.kalistenika.composable.language.ChangeLanguageScreen
import pl.matiu.kalistenika.composable.navigation.ChangeLanguage
import pl.matiu.kalistenika.composable.navigation.CreateSeries
import pl.matiu.kalistenika.composable.navigation.EditRepetitionSeries
import pl.matiu.kalistenika.composable.navigation.EditTimeSeries
import pl.matiu.kalistenika.composable.navigation.HistoryDetailsScreen
import pl.matiu.kalistenika.composable.navigation.HistoryScreen
import pl.matiu.kalistenika.composable.navigation.Home
import pl.matiu.kalistenika.composable.navigation.SeriesScreen
import pl.matiu.kalistenika.composable.series.AddExerciseButton
import pl.matiu.kalistenika.composable.series.RepetitionExerciseEditScreen
import pl.matiu.kalistenika.composable.series.SeriesScreen
import pl.matiu.kalistenika.composable.series.TimeExerciseEditScreen
import pl.matiu.kalistenika.composable.training.AddTrainingButton
import pl.matiu.kalistenika.composable.training.TrainingScreen
import pl.matiu.kalistenika.media.StartSong
import pl.matiu.kalistenika.notification.createNotificationChannel
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.ui.theme.KalistenikaTheme
import pl.matiu.kalistenika.ui.theme.MainScreenColor
import pl.matiu.kalistenika.ui.theme.Wheat

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createNotificationChannel(context = this)

        setContent {
            KalistenikaTheme {
                StartSong.init(LocalContext.current)
                KalistenikaApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun KalistenikaApp(viewModel: MainViewModel = hiltViewModel()) {

    val context = LocalContext.current
    val appLanguage = viewModel.appLanguage

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                Log.d("Notification", "Uprawnienia przyznane")
            } else {
                Log.e("Notification", "Brak uprawnień do powiadomień!")
            }
        }

        LaunchedEffect(Unit) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

//    Log.d("DisposableEffect", viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current).isActive
//    + " training name: " + viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current).trainingName +
//    " training id: " + viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current).trainingId +
//    " actual page: " + viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current).actualPage)

    KalistenikaTheme {
        val backStack = remember { mutableStateListOf<Any>(Home) }

        var topBarTitle by rememberSaveable { mutableStateOf("test") }
        var topBarPreviewScreen by rememberSaveable { mutableStateOf("") }
        var isNavigationIcon by rememberSaveable { mutableStateOf(false) }
        var addButton by rememberSaveable { mutableStateOf("") } //dodawanie ćwiczenia/treningu

        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerItem("Pobierz treningi")

                    Card {
                        NavigationDrawerItem(
                            label = { Text(text = "Zmień język aplikacji") },
                            selected = false,
                            onClick = {
//                                navController.navigate(route = AlternativeRoutes.ChangeLanguage.destination)
                                backStack.add(ChangeLanguage)
                            }
                        )
                    }
                }
            }) {
            Scaffold(
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MainScreenColor
                        ),
                        title = {
                            Text(
                                text = topBarTitle,
                                maxLines = 1,
                                color = Wheat,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                fontWeight = FontWeight.Bold
                            )
                        },

                        navigationIcon = {
                            if (isNavigationIcon) {
                                IconButton(onClick = {
                                    when (topBarPreviewScreen) {
                                        "history" -> {
                                            backStack.removeAt(backStack.size - 1)
//                                            navController.navigate(route = topBarPreviewScreen)
                                        }
                                        "training" -> {
                                            Log.d("training test", backStack.size.toString())
                                            backStack.removeAt(backStack.size - 1)
//                                            navController.navigate(route = topBarPreviewScreen)
                                        }
                                        else -> {
//                                            navController.popBackStack()
                                            backStack.removeAt(backStack.size - 1)
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = Wheat
                                    )
                                }
                            }
                        },

                        actions = {
                            IconButton(onClick = {
                                scope.launch {
                                    drawerState.apply {
                                        if (isClosed) open() else close()
                                    }
                                }
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Menu,
                                    contentDescription = "Localized description",
                                    tint = Wheat
                                )
                            }
                        }
                    )
                },
                bottomBar = {
                    BottomAppBar(backStack = backStack)
                },
                floatingActionButton = {
                    if (addButton == "AddTraining") {
                        AddTrainingButton(backStack)
                    }
                    if (addButton == "AddExercise") {
                        AddExerciseButton {
//                            navController.navigate(AlternativeRoutes.CreateSeries.destination + "/$trainingId")
                            backStack.add(CreateSeries(trainingName = "", trainingId = 1))//poprawic
                        }
                    }
                }
            ) { innerPadding ->
                val isSeriesActive = viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current)
                val destination = if(isSeriesActive.isActive == "false")
                    MainRoutes.Training.destination
                else
                    AlternativeRoutes.SeriesScreen.destination + "/${isSeriesActive.trainingName}" + "/${isSeriesActive.trainingId}"

                NavDisplay(
                    backStack = backStack,
                    onBack = { backStack.removeLastOrNull() },
                    modifier = Modifier.padding(innerPadding),
                    entryProvider = { key ->
                        when (key) {
                            is Home -> NavEntry(key) {
                                TrainingScreen(
                                    backStack = backStack,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    }
                                )
                            }

                            is SeriesScreen -> NavEntry(key) {

                                SeriesScreen(
                                    trainingName = key.trainingName,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    },
                                    backStack = backStack
                                )
                            }

                            is CreateSeries -> NavEntry(key) {
                                pl.matiu.kalistenika.composable.series.CreateSeries(
                                    trainingId = key.trainingId,
                                    trainingName = key.trainingName,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    },
                                    backStack = backStack
                                )
                            }

                            is EditTimeSeries -> NavEntry(key) {
                                TimeExerciseEditScreen(
                                    exerciseId = key.exerciseId,
                                    trainingId = key.trainingId,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    },
                                )
                            }

                            is EditRepetitionSeries -> NavEntry(key) {
                                RepetitionExerciseEditScreen(
                                    exerciseId = key.exerciseId,
                                    trainingId = key.trainingId,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    },
                                )
                            }

                            is ChangeLanguage -> NavEntry(key) {

                                ChangeLanguageScreen(
                                    appLanguage = appLanguage,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    }
                                )
                            }

                            is HistoryScreen -> NavEntry(key) {

                                HistoryScreen(
                                    backStack = backStack,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    }
                                )
                            }

                            is HistoryDetailsScreen -> NavEntry(key) {
                               HistoryDetailsScreen(
                                    backStack = backStack,
                                    date = key.date,
                                    onUpdateTopBar = { title, preview, icon, button ->
                                        topBarTitle = title
                                        topBarPreviewScreen = preview
                                        isNavigationIcon = icon
                                        addButton = button
                                    },
                                )
                            }

                            else -> NavEntry(Unit) { Text("Unknown route") }
                        }
                    }
                )
            }
        }
    }
}