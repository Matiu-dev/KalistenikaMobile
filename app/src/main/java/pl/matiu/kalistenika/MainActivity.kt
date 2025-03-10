package pl.matiu.kalistenika

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
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
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
import android.Manifest
import android.app.Activity
import android.app.LocaleManager
import android.content.res.Configuration
import android.os.LocaleList
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.ui.res.stringResource
import androidx.core.os.LocaleListCompat

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.media.StartSong
import pl.matiu.kalistenika.composable.history.HistoryScreen
import pl.matiu.kalistenika.composable.series.SeriesScreen
import pl.matiu.kalistenika.ui.theme.KalistenikaTheme
import pl.matiu.kalistenika.composable.series.AddExerciseButton
import pl.matiu.kalistenika.composable.training.AddTrainingButton
import pl.matiu.kalistenika.composable.series.CreateSeries
import pl.matiu.kalistenika.composable.series.RepetitionExerciseEditScreen
import pl.matiu.kalistenika.composable.series.TimeExerciseEditScreen
import pl.matiu.kalistenika.composable.training.TrainingScreen
import pl.matiu.kalistenika.ui.theme.MainScreenColor
import pl.matiu.kalistenika.ui.theme.Wheat
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.composable.history.HistoryDetailsScreen
import pl.matiu.kalistenika.composable.language.ChangeLanguageScreen
import pl.matiu.kalistenika.language.AppLanguage
import pl.matiu.kalistenika.notification.createNotificationChannel
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var appLanguage: AppLanguage

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //get exercise
//        NinjaApiService().getExercises()

//        var user = User("email", "password", Anonymous)

//        val loginContext = LoginContext(GoogleLogin())
//        val registerContext = RegisterContext(GoogleRegister())

        //logowanie
//        user.state.signIn(user, loginContext)
//        user.state.logOut(user, loginContext)

        //klikam na przycisk z napisem github i zmieniam strategie
//        loginContext.setStrategy(GithubLogin())
        //pojawia sie ekran z polami do uzupelnienia dla tej strategii logowania
        //po uzupelnieniu klikam na jeden z ponizszych przyciskow
//        user.state.signIn(user, loginContext)
//        user.state.signIn(user, loginContext)
//        user.state.signUp(user, registerContext)
//        user.state.logOut(user, LoginContext(GithubLogin()))
        //nastepuje logowanie lub wylogowanie

//        RealTimeDatabaseService().writeData()

        //notification
        createNotificationChannel(context = this)

        setContent {
            KalistenikaTheme {
                StartSong.init(LocalContext.current)
                KalistenikaApp(appLanguage)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun KalistenikaApp(appLanguage: AppLanguage) {

    val context = LocalContext.current

//    (context as ComponentActivity).changeLanguage("pl")

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
//                                    navController.navigate(topBarPreviewScreen)
                                    if (topBarPreviewScreen == "history") {
                                        navController.navigate(route = topBarPreviewScreen)
                                    } else {
                                        navController.popBackStack(topBarPreviewScreen, false)
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

                    if (addButton == "AddTraining") {
                        AddTrainingButton(navController = navController)
                    }

                    if (addButton == "AddExercise") {
                        AddExerciseButton { navController.navigate(AlternativeRoutes.CreateSeries.destination + "/$trainingId") }
                    }
                }
            ) { innerPadding ->

                //TODO przetestowac tabsy https://m3.material.io/components/tabs/overview

//                Navigator(TrainingListNavigation(navController = navController, innerPadding))

                NavHost(
                    navController = navController,
                    startDestination = MainRoutes.Training.destination,
                    modifier = Modifier.padding(innerPadding),
                ) {
                    composable(route = AlternativeRoutes.ChangeLanguage.destination) {
                        topBarTitle = stringResource(R.string.language_page_title)
                        topBarPreviewScreen = AlternativeRoutes.ChangeLanguage.topBarPreviewScreen
                        isNavigationIcon = AlternativeRoutes.ChangeLanguage.isNavigationIcon
                        addButton = AlternativeRoutes.ChangeLanguage.addButton

                        ChangeLanguageScreen(context = context, appLanguage = appLanguage, navController = navController)
                    }

                    composable(route = MainRoutes.Training.destination) {
                        topBarTitle = stringResource(R.string.main_page_title)
                        topBarPreviewScreen = MainRoutes.Training.destination
                        isNavigationIcon = MainRoutes.Training.isNavigationIcon
                        addButton = MainRoutes.Training.addButton

                        TrainingScreen(navController = navController)
                    }

                    composable(route = AlternativeRoutes.SeriesScreen.destination + "/{trainingName}" + "/{trainingId}") { backStackEntry ->

                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt() ?: 0
                        trainingName = backStackEntry.arguments?.getString("trainingName") ?: ""

                        topBarTitle = backStackEntry.arguments?.getString("trainingName").toString()
                        topBarPreviewScreen = AlternativeRoutes.SeriesScreen.topBarPreviewScreen
                        isNavigationIcon = AlternativeRoutes.SeriesScreen.isNavigationIcon
                        addButton = AlternativeRoutes.SeriesScreen.addButton

                        SeriesScreen(
                            navController = navController,
                            trainingName = backStackEntry.arguments?.getString("trainingName").toString()
                        )
                    }

                    composable(route = AlternativeRoutes.CreateSeries.destination + "/{trainingId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.CreateSeries.topBarTitle
                        topBarPreviewScreen =
                            AlternativeRoutes.CreateSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.CreateSeries.isNavigationIcon
                        addButton = AlternativeRoutes.CreateSeries.addButton

                        CreateSeries(
                            navController = navController,
                            trainingId = trainingId,
                            context = LocalContext.current,
                            trainingName = trainingName
                        )
                    }

                    composable(route = AlternativeRoutes.EditRepetitionSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.EditRepetitionSeries.topBarTitle
                        topBarPreviewScreen =
                            AlternativeRoutes.EditRepetitionSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.EditRepetitionSeries.isNavigationIcon
                        addButton = AlternativeRoutes.EditRepetitionSeries.addButton

                        RepetitionExerciseEditScreen(
                            navigator = navController,
                            exerciseId = exerciseId,
                            trainingId = trainingId
                        )
                    }

                    composable(route = AlternativeRoutes.EditTimeSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.EditTimeSeries.topBarTitle
                        topBarPreviewScreen =
                            AlternativeRoutes.EditTimeSeries.topBarPreviewScreen + "/${trainingName}" + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.EditTimeSeries.isNavigationIcon
                        addButton = AlternativeRoutes.EditTimeSeries.addButton

                        TimeExerciseEditScreen(
                            navigator = navController,
                            exerciseId = exerciseId,
                            trainingId = trainingId
                        )
                    }


                    composable(route = MainRoutes.History.destination) {
                        topBarTitle = MainRoutes.History.topBarTitle
                        isNavigationIcon = MainRoutes.History.isNavigationIcon
                        addButton = MainRoutes.History.addButton

                        HistoryScreen(navController = navController)
                    }

                    composable(route = AlternativeRoutes.HistoryDateDetails.destination + "/{date}") {

                        val date: String = it.arguments?.getString("date").toString()

                        topBarTitle = AlternativeRoutes.HistoryDateDetails.topBarTitle
                        topBarPreviewScreen =
                            AlternativeRoutes.HistoryDateDetails.topBarPreviewScreen
                        isNavigationIcon = AlternativeRoutes.HistoryDateDetails.isNavigationIcon
                        addButton = AlternativeRoutes.HistoryDateDetails.addButton

                        HistoryDetailsScreen(date = date)
                    }
                }
            }
        }

    }
}


