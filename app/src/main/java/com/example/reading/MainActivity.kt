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
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReadingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    // ViewModel 인스턴스를 여기서 한 번만 가져옵니다.
                    // ViewModelStoreOwner를 명시적으로 지정하여 액티비티 스코프에서 ViewModel을 공유합니다.
                    val sharedViewModel: UserRecommendationProfileViewModel = viewModel(
                        viewModelStoreOwner = LocalContext.current as ComponentActivity
                    )
                    Log.d("MainActivity", "Shared ViewModel Instance Hash: ${System.identityHashCode(sharedViewModel)}")


                    NavHost(navController = navController, startDestination = "start") {
                        composable("start") { StartScreen(navController = navController) }

                        // ViewModel 인스턴스를 각 화면 컴포저블에 인자로 직접 전달
                        composable("signup") {
                            SignupScreen(navController = navController, viewModel = sharedViewModel)
                        }
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        composable("consent") {
                            ConsentScreen(navController = navController)
                        }
                        composable("TodayDo") {
                            TodayDoScreen(navController = navController, viewModel = sharedViewModel)
                        }
                        composable("category") {
                            CategoryScreen(navController = navController, viewModel = sharedViewModel)
                        }
                        composable("home") {
                            HomeScreen(navController = navController, viewModel = sharedViewModel)
                        }

                        // BookContents 라우트: bookId를 인자로 받도록 설정
                        // BookContentsScreen에서 bookId가 nullable String? 타입이므로,
                        // NavArgument에서 getString()의 결과 (String?)를 그대로 전달합니다.
                        composable(
                            route = "BookContents/{bookId}",
                            arguments = listOf(navArgument("bookId") { type = NavType.StringType; nullable = true }) // nullable = true 추가
                        ) { backStackEntry ->
                            val bookId = backStackEntry.arguments?.getString("bookId")
                            BookContentsScreen(navController = navController, bookId = bookId)
                        }

                        composable("TodayRec") {
                            TodayRecScreen(navController = navController, viewModel = sharedViewModel)
                        }
                        composable("minilib") { MiniLibScreen(navController = navController)}
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
                        composable("notes") { ReadBookListScreen(navController = navController, viewModel = sharedViewModel) }
                        composable("eval"){ BookEvaluateScreen(navController) }
                        composable("report"){ BookReportScreen(navController) }
                        composable("quiz"){ QuizScreen (navController) }
                        composable("img"){MakeAiImgScreen(navController)}
                        composable("newimg"){ModifiedImageScreen(navController)}
                    }
                }
            }
        }
    }
}