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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.matiu.kalistenika.history.HistoryScreen
import pl.matiu.kalistenika.internalStorage.RepetitionExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TimeExerciseInternalStorage
import pl.matiu.kalistenika.internalStorage.TrainingInternalStorageService
import pl.matiu.kalistenika.logger.ConsoleLogger
import pl.matiu.kalistenika.logger.DateTimeLogger
import pl.matiu.kalistenika.logger.ThreadIdLogger
import pl.matiu.kalistenika.navigation.History
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.navigation.UserProfile
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
import pl.matiu.kalistenika.realtimeDatabase.RealTimeDatabaseService
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
fun Test() {
    val logger = DateTimeLogger(ThreadIdLogger(ConsoleLogger()))

    logger.log("test")
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
                                        if(isClosed) open() else close()
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
                        Row() {
                            //TODO https://developer.android.com/develop/ui/compose/components/fab
//                        ExtendedFloatingActionButton(onClick = { /*TODO*/ }) {
//
//                        }

                            ElevatedButton(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
//                        colors = ButtonDefaults.outlinedButtonColors(contentColor = ZielonyNapis),
                                onClick = {
                                    navController.navigate(Training.route)
                                }) {
                                Text(text = "Trening", color = Wheat)
                                Icon(
                                    painter = rememberVectorPainter(image = Training.icon),
                                    contentDescription = "icon", tint = Wheat
                                )
                            }

                            ElevatedButton(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                onClick = {
                                    navController.navigate(History.route)
                                }) {
                                Text(text = "Historia", color = Wheat)
                                Icon(
                                    painter = rememberVectorPainter(image = History.icon),
                                    contentDescription = "icon", tint = Wheat
                                )
                            }

                            ElevatedButton(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                                onClick = {
                                    navController.navigate(UserProfile.route)
                                }) {
                                Text(
                                    text = "Profil",
                                    color = Wheat
                                )
                                Icon(
                                    painter = rememberVectorPainter(image = UserProfile.icon),
                                    contentDescription = "icon", tint = Wheat
                                )
                            }
                        }

                    }
                },
                floatingActionButton = {

                    if (addButton == "AddTraining") {
                        AddTrainingButton { navController.navigate(Training.route + "/createTraining") }
//                    AddTrainingButton(navController = navController)

                    }

                    if (addButton == "AddExercise") {
                        AddExerciseButton { navController.navigate(Training.route + "/$trainingId" + "/createSeries") }
                    }
                }
            ) { innerPadding ->

                //TODO przetestowac tabsy https://m3.material.io/components/tabs/overview

                NavHost(
                    navController = navController,
                    startDestination = Training.route,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Training.route) {
                        topBarTitle = "Treningi"
                        topBarPreviewScreen = Training.route
                        isNavigationIcon = false
                        addButton = "AddTraining"

                        TrainingScreen(navController = navController)
                    }

                    composable(route = Training.route + "/createTraining") {
                        topBarTitle = "Kreator treningu"
                        topBarPreviewScreen = Training.route

                        isNavigationIcon = true
                        addButton = ""

                        CreateTraining(navController = navController, LocalContext.current)
                    }

                    composable(route = Training.route + "/{trainingId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        topBarTitle = TrainingInternalStorageService().getTrainingNameById(
                            LocalContext.current,
                            trainingId
                        )
                        topBarPreviewScreen = Training.route
                        isNavigationIcon = true
                        addButton = "AddExercise"

                        SeriesScreen(
                            navController = navController,
                            trainingId = trainingId
                        )
                    }

                    composable(route = Training.route + "/{trainingId}" + "/createSeries") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!

                        topBarTitle = "Kreator serii"
                        topBarPreviewScreen = Training.route + "/${trainingId}"
                        isNavigationIcon = true
                        addButton = ""

                        CreateSeries(
                            navController = navController,
                            trainingId = trainingId,
                            LocalContext.current,
                            TimeExerciseInternalStorage().getNumberOfExerciseForTrainingId(
                                LocalContext.current, trainingId,
                                TrainingInternalStorageService().getTrainingNameById(
                                    LocalContext.current,
                                    trainingId
                                )
                            )
                        )
                    }

                    composable(route = Training.route + "/{trainingId}" + "/editRepetitionExercise" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId =
                            backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = "Edytor ćwiczenia"
                        topBarPreviewScreen = Training.route + "/${trainingId}"
                        isNavigationIcon = true
                        addButton = ""

                        RepetitionExerciseEditScreen(
                            navigator = navController,
                            context = LocalContext.current,
                            exercise = RepetitionExerciseInternalStorage().findRepetitionExerciseById(
                                LocalContext.current,
                                exerciseId,
                                TrainingInternalStorageService().getTrainingNameById(
                                    LocalContext.current,
                                    trainingId
                                )
                            ), trainingId,
                            TimeExerciseInternalStorage().getNumberOfExerciseForTrainingId(
                                LocalContext.current, trainingId,
                                TrainingInternalStorageService().getTrainingNameById(
                                    LocalContext.current,
                                    trainingId
                                )
                            )
                        )
                    }

                    composable(route = Training.route + "/{trainingId}" + "/editTimeExercise" + "/{exerciseId}") { backStackEntry ->
                        trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                        val exerciseId =
                            backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                        topBarTitle = "Edytor ćwiczenia"
                        topBarPreviewScreen = Training.route + "/${trainingId}"
                        isNavigationIcon = true
                        addButton = ""

                        TimeExerciseEditScreen(
                            navigator = navController,
                            context = LocalContext.current,
                            exercise = TimeExerciseInternalStorage().findTimeExerciseById(
                                LocalContext.current,
                                exerciseId,
                                TrainingInternalStorageService().getTrainingNameById(
                                    LocalContext.current,
                                    trainingId
                                )
                            ), trainingId,
                            TimeExerciseInternalStorage().getNumberOfExerciseForTrainingId(
                                LocalContext.current, trainingId,
                                TrainingInternalStorageService().getTrainingNameById(
                                    LocalContext.current,
                                    trainingId
                                )
                            )
                        )
                    }

                    composable(route = History.route) {
                        topBarTitle = "Historia"
                        isNavigationIcon = false
                        addButton = ""

                        HistoryScreen()
                    }

                    composable(route = UserProfile.route) {
                        topBarTitle = "Profil"
                        isNavigationIcon = false
                        addButton = ""

                        UserProfileScreen()
                    }
                }
            }
        }

    }
}


