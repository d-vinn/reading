package com.example.reading

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.reading.ui.theme.ReadingTheme
import androidx.lifecycle.ViewModelStoreOwner
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
// Logcat 임포트 추가 (테스트용)
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    val activityViewModelStoreOwner = checkNotNull(LocalContext.current as? ViewModelStoreOwner) {
                        "Context is not a ViewModelStoreOwner"
                    }

                    // ViewModel 인스턴스를 여기서 한 번만 가져옵니다.
                    val sharedViewModel: UserRecommendationProfileViewModel = viewModel(viewModelStoreOwner = activityViewModelStoreOwner)
                    Log.d("MainActivity", "Shared ViewModel Instance Hash: ${System.identityHashCode(sharedViewModel)}")


                    NavHost(navController = navController, startDestination = "start") {
                        composable("start") { StartScreen(navController = navController) }

                        // ViewModel 인스턴스를 각 화면 컴포저블에 인자로 직접 전달
                        composable("signup") {
                            SignupScreen(navController = navController, viewModel = sharedViewModel) // <-- ViewModel 인자로 전달
                        }
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("consent") {
                            ConsentScreen(navController = navController)
                        }
                        composable("emotion") {
                            EmotionSelectionScreen(navController = navController)
                        }
                        composable("TodayDo") {
                            TodayDoScreen(navController = navController, viewModel = sharedViewModel) // <-- ViewModel 인자로 전달
                        }
                        composable("category") {
                            CategoryScreen(navController = navController, viewModel = sharedViewModel) // <-- ViewModel 인자로 전달 (만약 CategoryScreen에서 사용한다면)
                        }
                        composable("home") {
                            HomeScreen(navController = navController, viewModel = sharedViewModel) // <-- ViewModel 인자로 전달
                        }

                        composable("BookContents") {
                            BookContentsScreen(navController = navController)
                        }
                        composable("TodayRec") {
                            TodayRecScreen(navController = navController, viewModel = sharedViewModel) // <-- ViewModel 인자로 전달
                        }
                        composable("minilib") { MiniLibScreen(navController)}
                        composable("set") { SettingsScreen(navController)}
                        composable(
                            route = "bookCase/{bookshelfName}",
                            arguments = listOf(
                                navArgument("bookshelfName") {
                                    type = NavType.StringType
                                }
                            )
                        ) { backStackEntry ->
                            val bookshelfName = backStackEntry.arguments?.getString("bookshelfName") ?: ""
                            BookCaseScreen(navController = navController, bookshelfName = bookshelfName)
                        }
                        composable("notes") { ReadBookListScreen(navController) }
                        composable("eval"){ BookEvaluateScreen(navController) }
                        composable("report"){ BookReportScreen(navController) }
                        composable("quiz"){ QuizScreen (navController) }
                        composable("img"){MakeAiImgScreen(navController)}
                    }
                }
            }
        }
    }
}