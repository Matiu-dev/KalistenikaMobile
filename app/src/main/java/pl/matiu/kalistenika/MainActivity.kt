package pl.matiu.kalistenika

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.matiu.kalistenika.history.HistoryScreen
import pl.matiu.kalistenika.internalStorage.ExerciseInternalStorageService
import pl.matiu.kalistenika.navigation.History
import pl.matiu.kalistenika.navigation.Training
import pl.matiu.kalistenika.navigation.UserProfile
import pl.matiu.kalistenika.training.SeriesScreen
import pl.matiu.kalistenika.ui.theme.Beige
import pl.matiu.kalistenika.ui.theme.KalistenikaTheme
import pl.matiu.kalistenika.profile.UserProfileScreen
import pl.matiu.kalistenika.training.AddExerciseButton
import pl.matiu.kalistenika.training.AddTrainingButton
import pl.matiu.kalistenika.training.CreateSeries
import pl.matiu.kalistenika.training.CreateTraining
import pl.matiu.kalistenika.training.RepetitionExerciseEditScreen
import pl.matiu.kalistenika.training.TimeExerciseEditScreen
import pl.matiu.kalistenika.training.TrainingScreen
import pl.matiu.kalistenika.ui.theme.InsideLevel1
import pl.matiu.kalistenika.ui.theme.Smola
import pl.matiu.kalistenika.ui.theme.Wheat

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setTrainings()
        setSeries()

        setContent {
            KalistenikaApp()
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

        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = InsideLevel1
                    ),
                    title = {
                        Text(
                            text = topBarTitle,
                            maxLines = 1,
//                            overflow = ,
//                            fontSize = 50.sp,
                            color = Wheat,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth(),
                            fontWeight = FontWeight.Bold
//                                     .weight(1f)
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

                    //TODO navigation drawer
//                         actions = {
//                             IconButton(onClick = { /*TODO*/ }) {
//                                 Icon(
//                                     imageVector = Icons.Filled.Menu,
//                                     contentDescription = "Localized description"
//                                 )
//                             }
//                         }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = InsideLevel1,
                    contentColor = InsideLevel1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = InsideLevel1)
                ) {
                    Row() {
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

                if(addButton == "AddTraining") {
                    AddTrainingButton { navController.navigate(Training.route + "/createTraining") }
//                    AddTrainingButton(navController = navController)

                }

                if(addButton == "AddExercise") {
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

                    topBarTitle = trainingId.let { getTrainingNameById(it).toString() }.toString()
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
                        LocalContext.current
                    )
                }

                composable(route = Training.route + "/{trainingId}" + "/editRepetitionExercise" + "/{exerciseId}") { backStackEntry ->
                    trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                    val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                    topBarTitle = "Edytor ćwiczenia"
                    topBarPreviewScreen = Training.route + "/${trainingId}"
                    isNavigationIcon = true
                    addButton = ""
                    
                    RepetitionExerciseEditScreen(navigator = navController, context = LocalContext.current, exercise = ExerciseInternalStorageService().findRepetitionExerciseById(LocalContext.current, exerciseId), trainingId)
                }

                composable(route = Training.route + "/{trainingId}" + "/editTimeExercise" + "/{exerciseId}") { backStackEntry ->
                    trainingId = backStackEntry.arguments?.getString("trainingId")?.toInt()!!
                    val exerciseId = backStackEntry.arguments?.getString("exerciseId")?.toInt()!!

                    topBarTitle = "Edytor ćwiczenia"
                    topBarPreviewScreen = Training.route + "/${trainingId}"
                    isNavigationIcon = true
                    addButton = ""

                    TimeExerciseEditScreen(navigator = navController, context = LocalContext.current, exercise = ExerciseInternalStorageService().findTimeExerciseById(LocalContext.current, exerciseId), trainingId)
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


