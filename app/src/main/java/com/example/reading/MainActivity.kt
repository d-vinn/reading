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
import androidx.lifecycle.ViewModelStoreOwner // ViewModel 스코프 지정을 위해 필요
import androidx.compose.ui.platform.LocalContext // LocalContext 사용을 위해 필요
import androidx.lifecycle.viewmodel.compose.viewModel // viewModel 함수 사용을 위해 필요

// TODO: UserRecommendationProfileViewModel 클래스 별도 구현 필요

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableEdgeToEdge() // 필요하다면 주석 해제
        setContent {
            ReadingTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()

                    // 현재 Activity를 ViewModelStoreOwner로 가져와 ViewModel 스코프를 지정합니다.
                    val activityViewModelStoreOwner = checkNotNull(LocalContext.current as? ViewModelStoreOwner) {
                        "Context is not a ViewModelStoreOwner"
                    }

                    // 시작 화면은 "start"로 유지
                    NavHost(navController = navController, startDestination = "start") {
                        composable("start") { StartScreen(navController = navController) } // StartScreen 컴포저블 필요

                        // signup: ViewModel에 age, gender 저장
                        composable("signup") {
                            // Activity 스코프로 ViewModel 가져옴
                            val viewModel: UserRecommendationProfileViewModel = viewModel(viewModelStoreOwner = activityViewModelStoreOwner)
                            // SignupScreen 내부에서 viewModel()을 통해 ViewModel에 접근
                            SignupScreen(navController = navController)
                        }
                        // login
                        composable("login") {
                            LoginScreen(navController = navController)
                        }
                        // consent
                        composable("consent") {
                            ConsentScreen(navController = navController)
                        }
                        // emotion
                        composable("emotion") {
                            EmotionSelectionScreen(navController = navController)
                        }
                        // TodayDo: ViewModel에 userInterest 저장
                        // 라우트 이름 "TodayDo" 유지
                        composable("TodayDo") {
                            // Activity 스코프로 ViewModel 가져옴
                            val viewModel: UserRecommendationProfileViewModel = viewModel(viewModelStoreOwner = activityViewModelStoreOwner)
                            // TodayDoScreen 내부에서 viewModel()을 통해 ViewModel에 접근
                            TodayDoScreen(navController = navController)
                        }
                        // category: ViewModel에 selectedCategories, otherInterest 저장
                        // 라우트 이름 "category" 유지
                        composable("category") {
                            // Activity 스코프로 ViewModel 가져옴
                            val viewModel: UserRecommendationProfileViewModel = viewModel(viewModelStoreOwner = activityViewModelStoreOwner)
                            // CategoryScreen 내부에서 viewModel()을 통해 ViewModel에 접근
                            CategoryScreen(navController = navController)
                        }
                        // home: ViewModel에서 정보 읽어와 추천 API 호출 트리거
                        // 라우트 이름 "home" 유지
                        composable("home") {
                            // Activity 스코프로 ViewModel 가져옴
                            val viewModel: UserRecommendationProfileViewModel = viewModel(viewModelStoreOwner = activityViewModelStoreOwner)
                            // HomeScreen 내부에서 viewModel()을 통해 ViewModel에 접근
                            HomeScreen(navController = navController)
                        }

                        // BookContents: 책 상세 정보
                        // 라우트 이름 "BookContents" 유지 (인자 받지 않음)
                        composable("BookContents") {
                            // BookContentsScreen은 이제 네비게이션 인자로 bookId를 받지 않습니다.
                            // 필요하다면 ViewModel이나 다른 방법으로 bookId를 전달받거나 책 상세 정보를 ViewModel에서 관리해야 합니다.
                            BookContentsScreen(navController = navController)
                        }
                        // TodayRec: 추천 결과 표시
                        // 라우트 이름 "TodayRec" 유지
                        composable("TodayRec") {
                            // TodayRecScreen 내부에서 viewModel()을 통해 ViewModel에 접근하여 추천 결과 표시
                            TodayRecScreen(navController = navController)
                        }
                        // minilib
                        composable("minilib") { MiniLibScreen(navController)}

                        // bookCase 라우트는 기존대로 navArgument 유지 (이것은 사용자 프로필 수집과 무관한 데이터)
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
                        // notes
                        composable("notes") { ReadBookListScreen(navController) }
                        // eval
                        composable("eval"){ BookEvaluateScreen(navController) }
                        // report
                        composable("report"){ BookReportScreen(navController) }
                        // quiz
                        composable("quiz"){ QuizScreen (navController) }
                        // img
                        composable("img"){MakeAiImgScreen(navController)}
                    }
                }
            }
        }
    }
}