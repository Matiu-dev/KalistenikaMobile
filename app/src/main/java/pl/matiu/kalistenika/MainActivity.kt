package pl.matiu.kalistenika

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
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
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.history.HistoryScreen
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.DateTimeLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.ui.SeriesScreen
import pl.matiu.kalistenika.ui.theme.KalistenikaTheme
import pl.matiu.kalistenika.ui.UserProfileScreen
import pl.matiu.kalistenika.ui.AddExerciseButton
import pl.matiu.kalistenika.ui.AddTrainingButton
import pl.matiu.kalistenika.ui.CreateSeries
import pl.matiu.kalistenika.ui.CreateTraining
import pl.matiu.kalistenika.ui.RepetitionExerciseEditScreen
import pl.matiu.kalistenika.ui.TimeExerciseEditScreen
import pl.matiu.kalistenika.ui.TrainingScreen
import pl.matiu.kalistenika.ui.theme.MainScreenColor
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.ui.theme.Wheat
import pl.matiu.kalistenika.exerciseApi.NinjaApiService
import pl.matiu.kalistenika.room.ExerciseDatabaseService
import pl.matiu.kalistenika.room.TrainingDatabaseService
import pl.matiu.kalistenika.routes.AlternativeRoutes
import pl.matiu.kalistenika.routes.MainRoutes
import pl.matiu.kalistenika.viewModel.TrainingViewModel
import pl.matiu.kalistenika.ui.DrawerItem
import java.lang.Exception

class MainActivity : ComponentActivity() {

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

        setContent {
            KalistenikaApp()
//            Test2()
            Log.d("siema", "eniu")
        }
    }
}

@Composable
fun Test2() {
    LaunchedEffect(Unit) {
        try {
            val exerciseList = NinjaApiService().getExercises()
            exerciseList.forEach { exercise ->
                Log.d("e", "$exercise")
            }
        } catch (e: Exception) {
            Log.e("error", "failed", e)
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun KalistenikaApp() {

    KalistenikaTheme {
        val navController = rememberNavController()
        var topBarTitle by rememberSaveable { mutableStateOf("test") }
        var topBarPreviewScreen by rememberSaveable { mutableStateOf("") }
        var isNavigationIcon by rememberSaveable { mutableStateOf(false) }
        var addButton by rememberSaveable { mutableStateOf("") } //dodawanie ćwiczenia/treningu
        var trainingId by rememberSaveable { mutableIntStateOf(0) }
        var drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        val tabsScreen = MainRoutes.values().toList()
        var tabIndex by remember { mutableStateOf(0) }

        ModalNavigationDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                ModalDrawerSheet {
                    DrawerItem("Pobierz treningi")

                    Card {
                        NavigationDrawerItem(
                            label = { Text(text = "Zard w masterze(SPRAWDŹ TUTAJ!!)") },
                            selected = false,
                            onClick = {

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
                                    navController.navigate(topBarPreviewScreen)
                                }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = Smola
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
                },
                floatingActionButton = {

                    if (addButton == "AddTraining") {
                        AddTrainingButton { navController.navigate(MainRoutes.Training.destination + "/createTraining") }
//                    AddTrainingButton(navController = navController)

                    }

                    if (addButton == "AddExercise") {
                        AddExerciseButton { navController.navigate(AlternativeRoutes.CreateSeries.destination + "/$trainingId") }
                    }
                }
            ) { innerPadding ->

                //TODO przetestowac tabsy https://m3.material.io/components/tabs/overview

                NavHost(
                    navController = navController,
                    startDestination = MainRoutes.Training.destination,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = MainRoutes.Training.destination) {
                        topBarTitle = MainRoutes.Training.topBarTitle
                        topBarPreviewScreen = MainRoutes.Training.destination
                        isNavigationIcon = MainRoutes.Training.isNavigationIcon
                        addButton = MainRoutes.Training.addButton

                        TrainingScreen(navController = navController)
                    }

                    composable(route = AlternativeRoutes.CreateTraining.destination) {
                        topBarTitle = AlternativeRoutes.CreateTraining.topBarTitle
                        topBarPreviewScreen = AlternativeRoutes.CreateTraining.topBarPreviewScreen
                        isNavigationIcon = AlternativeRoutes.CreateTraining.isNavigationIcon
                        addButton = AlternativeRoutes.CreateTraining.addButton

                        CreateTraining(navController = navController, LocalContext.current)
                    }

                    composable(route = AlternativeRoutes.SeriesScreen.destination + "/{trainingId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        topBarTitle = TrainingDatabaseService().getAllTraining().filter { it.trainingId == trainingId }[0].name
                        topBarPreviewScreen = AlternativeRoutes.SeriesScreen.topBarPreviewScreen
                        isNavigationIcon = AlternativeRoutes.SeriesScreen.isNavigationIcon
                        addButton = AlternativeRoutes.SeriesScreen.addButton

                        SeriesScreen(
                            navController = navController,
                            trainingId = trainingId
                        )
                    }

                    composable(route = AlternativeRoutes.CreateSeries.destination + "/{trainingId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.CreateSeries.topBarTitle
                        topBarPreviewScreen = AlternativeRoutes.CreateSeries.topBarPreviewScreen + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.CreateSeries.isNavigationIcon
                        addButton = AlternativeRoutes.CreateSeries.addButton

                        CreateSeries(
                            navController = navController,
                            trainingId = trainingId,
                            context = LocalContext.current,
                            numberOfExercise = ExerciseDatabaseService().getAllRepetitionExercise().filter { it.trainingId == trainingId }.size
                                    + ExerciseDatabaseService().getAllTimeExercise().filter { it.trainingId == trainingId }.size
                        )
                    }

                    composable(route = AlternativeRoutes.EditRepetitionSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.EditRepetitionSeries.topBarTitle
                        topBarPreviewScreen = AlternativeRoutes.EditRepetitionSeries.topBarPreviewScreen + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.EditRepetitionSeries.isNavigationIcon
                        addButton = AlternativeRoutes.EditRepetitionSeries.addButton

                        RepetitionExerciseEditScreen(
                            navigator = navController,
                            exercise = ExerciseDatabaseService().getRepetitionSeriesById(exerciseId = exerciseId),
                            trainingId = trainingId,
                            numberOfExercise = ExerciseDatabaseService().getAllRepetitionExercise().filter { it.trainingId == trainingId }.size
                                    + ExerciseDatabaseService().getAllTimeExercise().filter { it.trainingId == trainingId }.size
                        )
                    }

                    composable(route = AlternativeRoutes.EditTimeSeries.destination + "/{trainingId}" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = AlternativeRoutes.EditTimeSeries.topBarTitle
                        topBarPreviewScreen = AlternativeRoutes.EditTimeSeries.topBarPreviewScreen + "/${trainingId}"
                        isNavigationIcon = AlternativeRoutes.EditTimeSeries.isNavigationIcon
                        addButton = AlternativeRoutes.EditTimeSeries.addButton

                        TimeExerciseEditScreen(
                            navigator = navController,
                            context = LocalContext.current,
                            exercise = ExerciseDatabaseService().getTimeSeriesById(exerciseId = exerciseId),
                            trainingId = trainingId,
                            numberOfExercise = ExerciseDatabaseService().getAllRepetitionExercise().filter { it.trainingId == trainingId }.size
                            + ExerciseDatabaseService().getAllTimeExercise().filter { it.trainingId == trainingId }.size
                        )
                    }

                    composable(route = MainRoutes.History.destination) {
                        topBarTitle = MainRoutes.History.topBarTitle
                        isNavigationIcon = MainRoutes.History.isNavigationIcon
                        addButton = MainRoutes.History.addButton

                        HistoryScreen()
                    }

//                    composable(route = "user_profile") {
//                        topBarTitle = "Profil"
//                        isNavigationIcon = false
//                        addButton = ""
//
//                        UserProfileScreen()
//                    }
                }
            }
        }

    }
}


