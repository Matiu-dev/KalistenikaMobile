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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.composable.history.HistoryDetailsScreen
import pl.matiu.kalistenika.composable.history.HistoryScreen
import pl.matiu.kalistenika.composable.language.ChangeLanguageScreen
import pl.matiu.kalistenika.composable.series.AddExerciseButton
import pl.matiu.kalistenika.composable.series.CreateSeries
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
        val navController = rememberNavController()
        var topBarTitle by rememberSaveable { mutableStateOf("test") }
        var topBarPreviewScreen by rememberSaveable { mutableStateOf("") }
        var isNavigationIcon by rememberSaveable { mutableStateOf(false) }
        var addButton by rememberSaveable { mutableStateOf("") } //dodawanie ćwiczenia/treningu
        var trainingId by rememberSaveable { mutableIntStateOf(0) }
        var trainingName by rememberSaveable { mutableStateOf("") }
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
                                navController.navigate(route = AlternativeRoutes.ChangeLanguage.destination)
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
                                            navController.navigate(route = topBarPreviewScreen)
                                        }
                                        "training" -> {
                                            navController.navigate(route = topBarPreviewScreen)
                                        }
                                        else -> {
                                            navController.popBackStack()
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
                    BottomAppBar(navController = navController)
                },
                floatingActionButton = {
                    if (addButton == "AddTraining") { AddTrainingButton(navController = navController) }
                    if (addButton == "AddExercise") { AddExerciseButton { navController.navigate(AlternativeRoutes.CreateSeries.destination + "/$trainingId") } }
                }
            ) { innerPadding ->
                //ToDo - poprawic, zeby asynchronicznie było
                val isSeriesActive = viewModel.sharedPrefsRepository.getIsSeriesActive(LocalContext.current)
                val destination = if(isSeriesActive.isActive == "false")
                    MainRoutes.Training.destination
                else
                    AlternativeRoutes.SeriesScreen.destination + "/${isSeriesActive.trainingName}" + "/${isSeriesActive.trainingId}"

//                Log.d("DisposableEffect", "/${isSeriesActive.trainingName}" + "/${isSeriesActive.trainingId}")

                NavHost(
                    navController = navController,
                    startDestination = destination,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(route = AlternativeRoutes.ChangeLanguage.destination) {
                        ChangeLanguageScreen(
                            appLanguage = appLanguage,
                            navController = navController,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            }
                        )
                    }

                    composable(route = MainRoutes.Training.destination) {
                        TrainingScreen(
                            navController = navController,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            }
                        )
                    }

                    composable(route = AlternativeRoutes.SeriesScreen.destination + "/{trainingName}" + "/{trainingId}") { backStackEntry ->

                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt() ?: 0
                        trainingName = backStackEntry.arguments?.getString("trainingName") ?: ""

                        SeriesScreen(
                            navController = navController,
                            trainingName = backStackEntry.arguments?.getString("trainingName").toString(),
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                            backStackEntry = backStackEntry
                        )
                    }

                    composable(route = AlternativeRoutes.CreateSeries.destination + "/{trainingId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        CreateSeries(
                            navController = navController,
                            trainingId = trainingId,
                            trainingName = trainingName,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                        )
                    }

                    composable(route = AlternativeRoutes.EditRepetitionSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        RepetitionExerciseEditScreen(
                            navigator = navController,
                            exerciseId = exerciseId,
                            trainingId = trainingId,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                        )
                    }

                    composable(route = AlternativeRoutes.EditTimeSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        TimeExerciseEditScreen(
                            navigator = navController,
                            exerciseId = exerciseId,
                            trainingId = trainingId,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                        )
                    }


                    composable(route = MainRoutes.History.destination) {

                        HistoryScreen(
                            navController = navController,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                        )
                    }

                    composable(route = AlternativeRoutes.HistoryDateDetails.destination + "/{date}") {

                        val date: String = it.arguments?.getString("date").toString()

                        HistoryDetailsScreen(
                            date = date,
                            onUpdateTopBar = { title, preview, icon, button ->
                                topBarTitle = title
                                topBarPreviewScreen = preview
                                isNavigationIcon = icon
                                addButton = button
                            },
                        )
                    }


                }
            }
        }
    }
}